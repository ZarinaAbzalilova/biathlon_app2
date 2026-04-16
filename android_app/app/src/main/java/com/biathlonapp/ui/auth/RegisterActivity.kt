package com.biathlonapp.ui.auth

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.biathlonapp.MainActivity
import com.biathlonapp.data.api.BiathlonApiService
import com.biathlonapp.data.repository.AuthRepository
import com.biathlonapp.databinding.ActivityRegisterBinding
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var authRepository: AuthRepository
    private lateinit var apiService: BiathlonApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        authRepository = AuthRepository(this)
        apiService = BiathlonApiService.create()

        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.buttonRegister.setOnClickListener {
            val email = binding.editEmail.text.toString().trim()
            val password = binding.editPassword.text.toString()
            val confirmPassword = binding.editConfirmPassword.text.toString()

            if (validateInput(email, password, confirmPassword)) {
                performRegistration(email, password)
            }
        }

        binding.textLogin.setOnClickListener {
            finish() // Возврат к логину
        }
    }

    private fun validateInput(email: String, password: String, confirmPassword: String): Boolean {
        if (email.isEmpty()) {
            binding.editEmail.error = "Введите email"
            return false
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.editEmail.error = "Некорректный email"
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
        if (password != confirmPassword) {
            binding.editConfirmPassword.error = "Пароли не совпадают"
            return false
        }
        return true
    }

    private fun performRegistration(email: String, password: String) {
        binding.progressBar.visibility = android.view.View.VISIBLE
        binding.buttonRegister.isEnabled = false

        lifecycleScope.launch {
            try {
                val response = apiService.register(mapOf(
                    "email" to email,
                    "password" to password
                ))

                if (response.isSuccessful && response.body()?.success == true) {
                    val authResponse = response.body()!!

                    authRepository.saveToken(authResponse.token)
                    authRepository.saveUserEmail(authResponse.user.email)
                    authRepository.saveUserId(authResponse.user.id)

                    Toast.makeText(this@RegisterActivity, "Регистрация успешна!", Toast.LENGTH_SHORT).show()

                    val intent = MainActivity.newIntent(this@RegisterActivity)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this@RegisterActivity, "Ошибка регистрации. Email уже существует?", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@RegisterActivity, "Ошибка: ${e.message}", Toast.LENGTH_SHORT).show()
            } finally {
                binding.progressBar.visibility = android.view.View.GONE
                binding.buttonRegister.isEnabled = true
            }
        }
    }

    companion object {
        fun newIntent(context: android.content.Context) = android.content.Intent(context, RegisterActivity::class.java)
    }
}