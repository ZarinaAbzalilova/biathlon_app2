package com.biathlonapp

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.biathlonapp.data.api.ApiClient
import com.biathlonapp.data.api.BiathlonApiService
import com.biathlonapp.data.repository.AuthRepository
import com.biathlonapp.databinding.ActivityMainBinding
import com.biathlonapp.ui.adapters.ViewPagerAdapter
import com.biathlonapp.ui.auth.LoginActivity
import com.biathlonapp.ui.auth.ResetPasswordActivity
import com.biathlonapp.ui.onboarding.OnboardingActivity
import com.biathlonapp.ui.settings.SettingsActivity
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private lateinit var authRepository: AuthRepository
    private lateinit var apiService: BiathlonApiService

    companion object {
        private const val PREFS_NAME = "onboarding_prefs"
        private const val KEY_ONBOARDING_COMPLETED = "onboarding_completed"

        fun newIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ✅ Проверяем онбординг ТОЛЬКО если приложение запускается впервые
        // При recreate (смена темы) savedInstanceState != null, поэтому пропускаем
        if (savedInstanceState == null) {
            val prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            val onboardingCompleted = prefs.getBoolean(KEY_ONBOARDING_COMPLETED, false)

            Log.d("MAIN_ACTIVITY", "onCreate, savedInstanceState == null, onboardingCompleted: $onboardingCompleted")

            if (!onboardingCompleted) {
                Log.d("MAIN_ACTIVITY", "Opening OnboardingActivity")
                startActivity(OnboardingActivity.newIntent(this))
                finish()
                return
            }
        } else {
            Log.d("MAIN_ACTIVITY", "onCreate, savedInstanceState != null (recreate), skipping onboarding check")
        }

        initializeApp()
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)

        Log.d("NOTIFICATION", "onNewIntent вызван")

        if (::binding.isInitialized) {
            handleNotificationIntent(intent)
            handleDeepLink(intent)
        }
    }

    private fun initializeApp() {
        Log.d("MAIN_ACTIVITY", "Initializing app...")

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
        updateFcmToken()

        // Проверяем, было ли приложение открыто из уведомления
        if (intent?.getBooleanExtra("open_race", false) == true) {
            handleNotificationIntent(intent)
        }

        // Обработка Deep Link (сброс пароля)
        handleDeepLink(intent)
    }

    private fun handleDeepLink(intent: Intent?) {
        val data = intent?.data
        if (data != null) {
            android.util.Log.d("DEEPLINK", "Scheme: ${data.scheme}")
            android.util.Log.d("DEEPLINK", "Host: ${data.host}")
            android.util.Log.d("DEEPLINK", "Path: ${data.path}")
            android.util.Log.d("DEEPLINK", "Query: ${data.queryParameterNames}")

            if (data.scheme == "biathlonapp" && data.host == "reset-password") {
                val token = data.getQueryParameter("token")
                android.util.Log.d("DEEPLINK", "Token: $token")
                if (!token.isNullOrEmpty()) {
                    startActivity(ResetPasswordActivity.newIntent(this, token))
                    finish()
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
            offscreenPageLimit = 4
        }
    }

    private fun setupBottomNavigation() {
        binding.bottomNavigation.setOnItemSelectedListener { menuItem ->
            val targetPosition = when (menuItem.itemId) {
                R.id.navigation_news -> 0
                R.id.navigation_team -> 1
                R.id.navigation_calendar -> 2
                R.id.navigation_favorites -> 3
                R.id.navigation_search -> 4
                else -> return@setOnItemSelectedListener false
            }

            binding.viewPager.setCurrentItem(targetPosition, false)
            true
        }
    }
}