package com.biathlonapp.ui.settings

import android.os.Bundle
import android.util.Log
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import com.biathlonapp.R
import com.biathlonapp.data.api.ApiClient
import com.biathlonapp.data.api.BiathlonApiService
import com.biathlonapp.data.repository.AuthRepository
import com.biathlonapp.data.repository.FavoritesRepository
import com.biathlonapp.databinding.ActivitySettingsBinding
import com.biathlonapp.ui.auth.LoginActivity
import kotlinx.coroutines.launch

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding
    private lateinit var authRepository: AuthRepository
    private lateinit var favoritesRepository: FavoritesRepository
    private lateinit var apiService: BiathlonApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Инициализируем зависимости
        apiService = ApiClient.apiService
        authRepository = AuthRepository(this)
        favoritesRepository = FavoritesRepository(this, apiService)

        // Применяем тему перед созданием UI
        applyTheme()

        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        loadUserInfo()
        setupClickListeners()
        setupNotificationSwitch()
        setupThemeSelector()
    }

    private fun applyTheme() {
        val prefs = getSharedPreferences("settings", MODE_PRIVATE)
        val themeMode = prefs.getInt("theme_mode", 0)

        when (themeMode) {
            0 -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            1 -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            2 -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Настройки"
        binding.toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun loadUserInfo() {
        val email = authRepository.getUserEmail()
        val isLoggedIn = authRepository.isLoggedIn()

        if (isLoggedIn) {
            binding.textUserEmail.text = email ?: "Не авторизован"
            binding.buttonAuth.text = "Выйти"
            binding.buttonAuth.backgroundTintList = androidx.core.content.ContextCompat.getColorStateList(
                this, android.R.color.holo_red_dark
            )
        } else {
            binding.textUserEmail.text = "Вы не авторизованы"
            binding.buttonAuth.text = "Войти"
            binding.buttonAuth.backgroundTintList = androidx.core.content.ContextCompat.getColorStateList(
                this, R.color.primary_green
            )
        }
    }

    private fun setupClickListeners() {
        binding.buttonAuth.setOnClickListener {
            if (authRepository.isLoggedIn()) {
                showLogoutDialog()
            } else {
                // Переход на экран логина
                startActivity(LoginActivity.newIntent(this))
                finish()
            }
        }
    }

    private fun setupNotificationSwitch() {
        val prefs = getSharedPreferences("settings", MODE_PRIVATE)
        val isEnabled = prefs.getBoolean("notifications_enabled", true)
        binding.switchNotifications.isChecked = isEnabled

        binding.switchNotifications.setOnCheckedChangeListener { _, isChecked ->
            prefs.edit().putBoolean("notifications_enabled", isChecked).apply()

            // Отправляем настройку на сервер только если пользователь авторизован
            if (authRepository.isLoggedIn()) {
                updateNotificationSettingsOnServer(isChecked)
            }

            val message = if (isChecked) "Уведомления включены" else "Уведомления выключены"
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateNotificationSettingsOnServer(enabled: Boolean) {
        lifecycleScope.launch {
            val token = authRepository.getToken()
            if (token != null) {
                try {
                    apiService.updateNotificationSettings("Bearer $token", mapOf("enabled" to enabled))
                } catch (e: Exception) {
                    // Ошибка, но локально сохранили
                }
            }
        }
    }

    private fun setupThemeSelector() {
        val prefs = getSharedPreferences("settings", MODE_PRIVATE)
        val themeMode = prefs.getInt("theme_mode", 0)

        when (themeMode) {
            0 -> binding.radioSystem.isChecked = true
            1 -> binding.radioLight.isChecked = true
            2 -> binding.radioDark.isChecked = true
        }

        binding.themeRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            val newThemeMode = when (checkedId) {
                R.id.radioLight -> 1
                R.id.radioDark -> 2
                else -> 0
            }

            prefs.edit().putInt("theme_mode", newThemeMode).apply()

            when (newThemeMode) {
                0 -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                1 -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                2 -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }

            Toast.makeText(this, "Тема изменена, перезапустите приложение", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showLogoutDialog() {
        androidx.appcompat.app.AlertDialog.Builder(this)
            .setTitle("Выход из системы")
            .setMessage("Вы уверены, что хотите выйти?")
            .setPositiveButton("Выйти") { _, _ ->
                performLogout()
            }
            .setNegativeButton("Отмена", null)
            .show()
    }

    private fun performLogout() {
        lifecycleScope.launch {
            try {
                favoritesRepository.clearAllFavorites()
                authRepository.clear()

                Toast.makeText(this@SettingsActivity, "Вы вышли из системы", Toast.LENGTH_SHORT).show()

                // Перезапускаем SettingsActivity, чтобы обновить UI
                recreate()
            } catch (e: Exception) {
                Toast.makeText(this@SettingsActivity, "Ошибка при выходе: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        fun newIntent(context: android.content.Context) = android.content.Intent(context, SettingsActivity::class.java)
    }
}