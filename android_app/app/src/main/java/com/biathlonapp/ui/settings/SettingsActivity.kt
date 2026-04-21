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
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.launch
import androidx.lifecycle.lifecycleScope

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

        // ← ВЫЗЫВАЕМ МЕТОД ВНУТРИ onCreate
        showFcmToken()
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
            updateNotificationSettingsOnServer(isChecked)
            val message = if (isChecked) "Уведомления включены" else "Уведомления выключены"
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun showFcmToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val token = task.result
                android.util.Log.d("FCM_TOKEN", "======================================")
                android.util.Log.d("FCM_TOKEN", "Полный FCM токен:")
                android.util.Log.d("FCM_TOKEN", token)
                android.util.Log.d("FCM_TOKEN", "Длина токена: ${token.length} символов")
                android.util.Log.d("FCM_TOKEN", "======================================")

                // Скопировать в буфер обмена для удобства
                val clipboard = getSystemService(CLIPBOARD_SERVICE) as android.content.ClipboardManager
                val clip = android.content.ClipData.newPlainText("FCM Token", token)
                clipboard.setPrimaryClip(clip)

                Toast.makeText(
                    this@SettingsActivity,
                    "Токен скопирован в буфер обмена! Длина: ${token.length}",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                android.util.Log.e("FCM_TOKEN", "Ошибка: ${task.exception?.message}")
            }
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
    // В методе sendTokenToServer используйте lifecycleScope
    private fun sendTokenToServer(fcmToken: String) {
        lifecycleScope.launch {  // ← lifecycleScope доступен в AppCompatActivity
            val jwtToken = authRepository.getToken()
            if (jwtToken != null) {
                try {
                    val response = apiService.updateFcmToken("Bearer $jwtToken", mapOf("fcm_token" to fcmToken))
                    if (response.isSuccessful) {
                        Log.d("FCM_TOKEN", "✅ Токен отправлен на сервер")
                    }
                } catch (e: Exception) {
                    Log.e("FCM_TOKEN", "Ошибка: ${e.message}")
                }
            }
        }
    }
    private fun performLogout() {
        lifecycleScope.launch {
            try {
                favoritesRepository.clearAllFavorites()
                authRepository.clear()

                Toast.makeText(this@SettingsActivity, "Вы вышли из системы", Toast.LENGTH_SHORT).show()

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