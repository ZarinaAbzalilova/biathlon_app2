package com.biathlonapp.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.biathlonapp.MainActivity
import com.biathlonapp.R
import com.biathlonapp.data.api.BiathlonApiService
import com.biathlonapp.data.repository.AuthRepository
import com.biathlonapp.data.repository.FavoritesRepository
import com.biathlonapp.databinding.ActivityLoginBinding
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var authRepository: AuthRepository
    private lateinit var apiService: BiathlonApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        authRepository = AuthRepository(this)
        apiService = BiathlonApiService.create()

        // Если уже авторизован, переходим в MainActivity
        if (authRepository.isLoggedIn()) {
            startMainActivity()
            return
        }

        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.buttonLogin.setOnClickListener {
            val email = binding.editEmail.text.toString().trim()
            val password = binding.editPassword.text.toString()

            if (validateInput(email, password)) {
                performLogin(email, password)
            }
        }

        binding.textRegister.setOnClickListener {
            startActivity(RegisterActivity.newIntent(this))
        }
        binding.textForgotPassword.setOnClickListener {
            showForgotPasswordDialog()
        }
    }
    private fun showForgotPasswordDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_forgot_password, null)
        val editEmail = dialogView.findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.editEmail)

        androidx.appcompat.app.AlertDialog.Builder(this)
            .setTitle("Сброс пароля")
            .setView(dialogView)
            .setPositiveButton("Отправить") { _, _ ->
                val email = editEmail.text.toString().trim()
                if (email.isNotEmpty()) {
                    requestPasswordReset(email)
                } else {
                    Toast.makeText(this, "Введите email", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Отмена", null)
            .show()
    }
    private fun requestPasswordReset(email: String) {
        lifecycleScope.launch {
            try {
                val response = apiService.forgotPassword(mapOf("email" to email))
                if (response.isSuccessful && response.body()?.success == true) {
                    Toast.makeText(
                        this@LoginActivity,
                        response.body()?.message ?: "Инструкция отправлена на email",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    Toast.makeText(this@LoginActivity, "Ошибка отправки", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@LoginActivity, "Ошибка: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun validateInput(email: String, password: String): Boolean {
        if (email.isEmpty()) {
            binding.editEmail.error = "Введите email"
            return false
        }
        if (password.isEmpty()) {
            binding.editPassword.error = "Введите пароль"
            return false
        }
        if (password.length < 6) {
            binding.editPassword.error = "Пароль должен быть не менее 6 символов"
            return false
        }
        return true
    }

    private fun performLogin(email: String, password: String) {
        binding.progressBar.visibility = android.view.View.VISIBLE
        binding.buttonLogin.isEnabled = false

        lifecycleScope.launch {
            try {
                val response = apiService.login(mapOf(
                    "email" to email,
                    "password" to password
                ))

                if (response.isSuccessful && response.body()?.success == true) {
                    val authResponse = response.body()!!

                    // Сохраняем данные авторизации
                    authRepository.saveToken(authResponse.token)
                    authRepository.saveUserEmail(authResponse.user.email)
                    authRepository.saveUserId(authResponse.user.id)

                    // ✅ СИНХРОНИЗИРУЕМ ИЗБРАННОЕ С СЕРВЕРОМ
                    syncFavorites(authResponse.token)

                    Toast.makeText(this@LoginActivity, "Добро пожаловать!", Toast.LENGTH_SHORT).show()
                    startMainActivity()
                } else {
                    Toast.makeText(this@LoginActivity, "Неверный email или пароль", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@LoginActivity, "Ошибка: ${e.message}", Toast.LENGTH_SHORT).show()
            } finally {
                binding.progressBar.visibility = android.view.View.GONE
                binding.buttonLogin.isEnabled = true
            }
        }
    }

    // Добавьте этот метод
    private suspend fun syncFavorites(token: String) {
        try {
            val response = apiService.getFavorites("Bearer $token")
            var favoritesRepository = FavoritesRepository(this, apiService)
            if (response.isSuccessful && response.body() != null) {
                val favorites = response.body() ?: emptyList()

                // Очищаем локальное избранное
                favoritesRepository.clearAllFavorites()

                // Сохраняем избранное с сервера
                favorites.forEach { athlete ->
                    favoritesRepository.addToFavorites(athlete)
                }

                android.util.Log.d("Sync", "✅ Synced ${favorites.size} favorites from server")
            }
        } catch (e: Exception) {
            android.util.Log.e("Sync", "Error syncing favorites: ${e.message}")
        }
    }
    private suspend fun sendFcmTokenToServer(token: String, userToken: String) {
        try {
            val response = apiService.updateFcmToken("Bearer $userToken", mapOf("fcm_token" to token))
            if (!response.isSuccessful) {
                android.util.Log.e("FCM", "Failed to send token")
            }
        } catch (e: Exception) {
            android.util.Log.e("FCM", "Error sending token: ${e.message}")
        }
    }
    private fun startMainActivity() {
        // Устанавливаем флаг, что приложение активно (пользователь вошел)
        val prefs = getSharedPreferences("onboarding_prefs", MODE_PRIVATE)
        prefs.edit()
            .putBoolean("app_is_active", true)
            .putBoolean("onboarding_completed", true)
            .apply()

        val intent = MainActivity.newIntent(this).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        startActivity(intent)
        finish()
    }

    companion object {
        fun newIntent(context: android.content.Context) = android.content.Intent(context, LoginActivity::class.java)
    }
}