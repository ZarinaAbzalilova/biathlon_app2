package com.biathlonapp.ui.auth

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.biathlonapp.MainActivity
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

    private fun startMainActivity() {
        val intent = MainActivity.newIntent(this)
        startActivity(intent)
        finish()
    }

    companion object {
        fun newIntent(context: android.content.Context) = android.content.Intent(context, LoginActivity::class.java)
    }
}