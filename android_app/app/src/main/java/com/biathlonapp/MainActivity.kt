package com.biathlonapp

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.biathlonapp.data.api.ApiClient
import com.biathlonapp.data.api.BiathlonApiService
import com.biathlonapp.data.repository.AuthRepository
import com.biathlonapp.databinding.ActivityMainBinding
import com.biathlonapp.ui.adapters.ViewPagerAdapter
import com.biathlonapp.ui.auth.LoginActivity
import com.biathlonapp.ui.auth.ResetPasswordActivity
import com.biathlonapp.ui.onboarding.OnboardingActivity
import com.biathlonapp.ui.settings.SettingsActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private lateinit var authRepository: AuthRepository
    private lateinit var apiService: BiathlonApiService

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Проверяем, нужно ли показать онбординг
        val prefs = getSharedPreferences("onboarding_prefs", Context.MODE_PRIVATE)
        val onboardingCompleted = prefs.getBoolean("onboarding_completed", false)

        if (!onboardingCompleted) {
            startActivity(OnboardingActivity.newIntent(this))
            finish()
            return
        }
        // Запрос разрешения на уведомления для Android 13+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissions(arrayOf(android.Manifest.permission.POST_NOTIFICATIONS), 1001)
        }

        authRepository = AuthRepository(this)
        apiService = ApiClient.apiService


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonSettings.setOnClickListener {
            startActivity(SettingsActivity.newIntent(this))
        }

        setupToolbar()
        setupViewPager()
        setupBottomNavigation()
        handleNotificationIntent(intent)
        updateFcmToken()  // ← Теперь работает
        // Проверяем, было ли приложение открыто из уведомления
        if (intent?.getBooleanExtra("open_race", false) == true) {
            handleNotificationIntent(intent)
        }

        // Обработка Deep Link (сброс пароля)
        handleDeepLink(intent)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        Log.d("NOTIFICATION", "onNewIntent вызван")
        handleNotificationIntent(intent)
        handleDeepLink(intent)
    }
    private fun handleDeepLink(intent: Intent?) {
        val data = intent?.data
        if (data != null) {
            // Обработка HTTPS ссылки
            if (data.scheme == "https" && data.host == "biathlon-app2.onrender.com" && data.path == "/reset-password") {
                val token = data.getQueryParameter("token")
                if (!token.isNullOrEmpty()) {
                    startActivity(ResetPasswordActivity.newIntent(this, token))
                }
            }
            // Обработка кастомной схемы (для обратной совместимости)
            else if (data.scheme == "biathlonapp" && data.host == "reset-password") {
                val token = data.getQueryParameter("token")
                if (!token.isNullOrEmpty()) {
                    startActivity(ResetPasswordActivity.newIntent(this, token))
                }
            }
        }
    }
    private fun updateFcmToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val token = task.result
                val jwtToken = authRepository.getToken()
                if (jwtToken != null) {
                    // Используем lifecycleScope.launch для вызова suspend функции
                    lifecycleScope.launch {
                        try {
                            val response = apiService.updateFcmToken("Bearer $jwtToken", mapOf("fcm_token" to token))
                            if (response.isSuccessful) {
                                Log.d("FCM", "✅ Токен автоматически обновлен")
                            } else {
                                Log.e("FCM", "Ошибка: ${response.code()}")
                            }
                        } catch (e: Exception) {
                            Log.e("FCM", "Ошибка обновления токена: ${e.message}")
                        }
                    }
                }
            } else {
                Log.e("FCM", "Ошибка получения токена FCM", task.exception)
            }
        }
    }

    private fun handleNotificationIntent(intent: Intent?) {
        val shouldOpenRace = intent?.getBooleanExtra("open_race", false) ?: false
        val raceId = intent?.getStringExtra("race_id") ?: ""

        Log.d("NOTIFICATION", "========== ОБРАБОТКА НАЖАТИЯ ==========")
        Log.d("NOTIFICATION", "Intent: $intent")
        Log.d("NOTIFICATION", "shouldOpenRace: $shouldOpenRace")
        Log.d("NOTIFICATION", "raceId: $raceId")
        Log.d("NOTIFICATION", "Все extras: ${intent?.extras}")

        if (shouldOpenRace && raceId.isNotEmpty()) {
            Log.d("NOTIFICATION", "Открываем протокол гонки: $raceId")
            val raceIntent = Intent(this, com.biathlonapp.ui.raceprotocol.RaceProtocolActivity::class.java)
            raceIntent.putExtra("race_id", raceId)
            startActivity(raceIntent)
        } else if (shouldOpenRace) {
            Log.d("NOTIFICATION", "Открываем главное приложение")
        } else {
            Log.d("NOTIFICATION", "Нет данных для открытия")
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        supportActionBar?.title = "Биатлон"

        binding.toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_settings -> {
                    startActivity(SettingsActivity.newIntent(this))
                    true
                }
                else -> false
            }
        }
    }

    private fun setupViewPager() {
        viewPagerAdapter = ViewPagerAdapter(this)
        binding.viewPager.apply {
            adapter = viewPagerAdapter
            isUserInputEnabled = false
        }
    }

    private fun setupBottomNavigation() {
        binding.bottomNavigation.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_news -> {
                    binding.viewPager.currentItem = 0
                    true
                }
                R.id.navigation_team -> {
                    binding.viewPager.currentItem = 1
                    true
                }
                R.id.navigation_calendar -> {
                    binding.viewPager.currentItem = 2
                    true
                }
                R.id.navigation_favorites -> {
                    binding.viewPager.currentItem = 3
                    true
                }
                R.id.navigation_search -> {
                    binding.viewPager.currentItem = 4
                    true
                }
                else -> false
            }
        }
    }
}