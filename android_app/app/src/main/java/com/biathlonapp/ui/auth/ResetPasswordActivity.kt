package com.biathlonapp.ui.auth

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.biathlonapp.data.api.ApiClient
import com.biathlonapp.data.api.BiathlonApiService
import com.biathlonapp.databinding.ActivityResetPasswordBinding
import kotlinx.coroutines.launch

class ResetPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResetPasswordBinding
    private lateinit var apiService: BiathlonApiService
    private var resetToken: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResetPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        apiService = ApiClient.apiService

        // Получаем токен из intent (через Deep Link)
        resetToken = intent.data?.getQueryParameter("token") ?: intent.getStringExtra("token")

        if (resetToken.isNullOrEmpty()) {
            Toast.makeText(this, "Недействительная ссылка", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        setupToolbar()
        setupClickListeners()
        verifyToken()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Сброс пароля"
        binding.toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun verifyToken() {
        lifecycleScope.launch {
            try {
                val response = apiService.verifyResetToken(resetToken!!)
                if (response.isSuccessful && response.body()?.valid == true) {
                    // Токен валиден
                    Toast.makeText(this@ResetPasswordActivity,
                        "Введите новый пароль", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@ResetPasswordActivity,
                        "Ссылка недействительна или истекла", Toast.LENGTH_LONG).show()
                    finish()
                }
            } catch (e: Exception) {
                Toast.makeText(this@ResetPasswordActivity,
                    "Ошибка проверки ссылки", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    private fun setupClickListeners() {
        binding.buttonReset.setOnClickListener {
            val newPassword = binding.editNewPassword.text.toString()
            val confirmPassword = binding.editConfirmPassword.text.toString()

            if (validatePasswords(newPassword, confirmPassword)) {
                resetPassword(newPassword)
            }
        }
    }

    private fun validatePasswords(password: String, confirm: String): Boolean {
        if (password.isEmpty()) {
            binding.editNewPassword.error = "Введите пароль"
            return false
        }
        if (password.length < 6) {
            binding.editNewPassword.error = "Пароль должен быть не менее 6 символов"
            return false
        }
        if (password != confirm) {
            binding.editConfirmPassword.error = "Пароли не совпадают"
            return false
        }
        return true
    }

    private fun resetPassword(newPassword: String) {
        lifecycleScope.launch {
            binding.progressBar.visibility = android.view.View.VISIBLE
            binding.buttonReset.isEnabled = false

            try {
                val response = apiService.resetPassword(mapOf(
                    "token" to resetToken!!,
                    "new_password" to newPassword
                ))

                if (response.isSuccessful && response.body()?.success == true) {
                    Toast.makeText(this@ResetPasswordActivity,
                        "Пароль успешно изменен! Войдите в аккаунт.", Toast.LENGTH_LONG).show()

                    startActivity(LoginActivity.newIntent(this@ResetPasswordActivity))
                    finish()
                } else {
                    Toast.makeText(this@ResetPasswordActivity,
                        "Ошибка: ${response.body()?.message}", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@ResetPasswordActivity,
                    "Ошибка: ${e.message}", Toast.LENGTH_SHORT).show()
            } finally {
                binding.progressBar.visibility = android.view.View.GONE
                binding.buttonReset.isEnabled = true
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    companion object {
        fun newIntent(context: android.content.Context, token: String): android.content.Intent {
            return android.content.Intent(context, ResetPasswordActivity::class.java).apply {
                putExtra("token", token)
            }
        }
    }
}