package com.biathlonapp.ui.settings

import android.os.Bundle
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
    private lateinit var apiService: BiathlonApiService  // ← ДОБАВЛЯЕМ

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Инициализируем зависимости
        apiService = ApiClient.apiService  // ← ВАЖНО!
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
        val themeMode = prefs.getInt("theme_mode", 0) // 0=системная, 1=светлая, 2=темная

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
        binding.textUserEmail.text = email ?: "Не авторизован"
    }

    private fun setupClickListeners() {
        binding.buttonLogout.setOnClickListener {
            showLogoutDialog()
        }
    }

    private fun setupNotificationSwitch() {
        val prefs = getSharedPreferences("settings", MODE_PRIVATE)
        val isEnabled = prefs.getBoolean("notifications_enabled", true)
        binding.switchNotifications.isChecked = isEnabled

        binding.switchNotifications.setOnCheckedChangeListener { _, isChecked ->
            prefs.edit().putBoolean("notifications_enabled", isChecked).apply()
            val message = if (isChecked) "Уведомления включены" else "Уведомления выключены"
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupThemeSelector() {
        val prefs = getSharedPreferences("settings", MODE_PRIVATE)
        val themeMode = prefs.getInt("theme_mode", 0)

        // Устанавливаем выбранный вариант
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

            // Применяем тему
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
                // Очищаем локальное избранное
                favoritesRepository.clearAllFavorites()

                // Очищаем данные авторизации
                authRepository.clear()

                Toast.makeText(this@SettingsActivity, "Вы вышли из системы", Toast.LENGTH_SHORT).show()

                // Переходим на экран логина
                val intent = LoginActivity.newIntent(this@SettingsActivity)
                intent.flags = android.content.Intent.FLAG_ACTIVITY_NEW_TASK or android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            } catch (e: Exception) {
                Toast.makeText(this@SettingsActivity, "Ошибка при выходе: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        fun newIntent(context: android.content.Context) = android.content.Intent(context, SettingsActivity::class.java)
    }
}