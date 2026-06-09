package com.biathlonapp.ui.onboarding

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.biathlonapp.MainActivity
import com.biathlonapp.R
import android.os.Handler
import android.os.Looper
import com.biathlonapp.databinding.ActivityOnboardingBinding
import com.biathlonapp.ui.auth.LoginActivity
import com.google.android.material.button.MaterialButton

class OnboardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnboardingBinding
    private lateinit var viewPagerAdapter: OnboardingPagerAdapter
    private lateinit var sharedPreferences: SharedPreferences
    private val handler = Handler(Looper.getMainLooper())
    private var autoAdvanceRunnable: Runnable? = null
    private var autoAdvanceEnabled = true
    private var isFirstLaunch = false
    private var onboardingCompleted = false

    companion object {
        private const val PREFS_NAME = "onboarding_prefs"
        private const val KEY_ONBOARDING_COMPLETED = "onboarding_completed"
        private const val KEY_APP_FIRST_LAUNCH = "app_first_launch"

        fun newIntent(context: Context): Intent {
            return Intent(context, OnboardingActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

        isFirstLaunch = sharedPreferences.getBoolean(KEY_APP_FIRST_LAUNCH, true)
        onboardingCompleted = sharedPreferences.getBoolean(KEY_ONBOARDING_COMPLETED, false)
        val appIsActive = sharedPreferences.getBoolean("app_is_active", false)

        Log.d("ONBOARDING", "isFirstLaunch: $isFirstLaunch, onboardingCompleted: $onboardingCompleted, appIsActive: $appIsActive")

        // Если приложение уже активно - сразу переходим в MainActivity
        if (appIsActive) {
            Log.d("ONBOARDING", "App is active, skipping onboarding")
            startActivity(MainActivity.newIntent(this))
            finish()
            return
        }

        setupViewPager()
        setupDotsIndicator()
        setupButtons()

        if (isFirstLaunch) {
            sharedPreferences.edit().putBoolean(KEY_APP_FIRST_LAUNCH, false).apply()
        }
    }

    private fun setupViewPager() {
        val fullOnboardingItems = listOf(
            OnboardingItem(
                title = "Добро пожаловать в Биатлон!",
                description = "Ваш личный помощник в мире биатлона. Следите за любимыми спортсменами, результатами гонок и новостями.",
                imageRes = R.drawable.ic_biathlon_logo,
                gifRes = R.raw.onboarding_welcome
            ),
            OnboardingItem(
                title = "Поиск спортсменов",
                description = "Быстрый поиск любого биатлониста по имени или фамилии. Вся информация о спортсмене в одном месте.",
                imageRes = R.drawable.ic_onboarding_search,
                gifRes = null
            ),
            OnboardingItem(
                title = "Результаты и статистика",
                description = "Детальная статистика выступлений, места, промахи. Анализируйте результаты любимых спортсменов.",
                imageRes = R.drawable.ic_onboarding_stats,
                gifRes = null
            ),
            OnboardingItem(
                title = "Уведомления о гонках",
                description = "Не пропустите ни одной гонки! Получайте напоминания за день и в день соревнований.",
                imageRes = R.drawable.ic_onboarding_notification,
                gifRes = null
            )
        )

        // Если первый запуск - показываем все 4 страницы
        // Если повторный - только первую страницу (быстрый старт)
        val onboardingItems = if (isFirstLaunch) {
            fullOnboardingItems
        } else {
            listOf(fullOnboardingItems[0])  // Только первая страница с GIF
        }

        viewPagerAdapter = OnboardingPagerAdapter(onboardingItems)
        binding.viewPager.adapter = viewPagerAdapter

        // Запускаем таймер для первой страницы (и при первом, и при повторном запуске)
        if (onboardingItems[0].gifRes != null) {
            scheduleAutoAdvance()
        }

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                updateDotsIndicator(position)
                updateButtons(position)

                if (position == 0 && viewPagerAdapter.itemCount == 1) {
                    // Для быстрого старта не перезапускаем таймер при возврате
                    // (но возврата быть не может, т.к. страница одна)
                } else if (position == 0) {
                    // При возврате на первую страницу в полном онбординге
                    autoAdvanceEnabled = true
                    scheduleAutoAdvance()
                } else {
                    autoAdvanceRunnable?.let { handler.removeCallbacks(it) }
                }
            }
        })
    }

    private fun scheduleAutoAdvance() {
        autoAdvanceRunnable?.let { handler.removeCallbacks(it) }

        autoAdvanceRunnable = Runnable {
            Log.d("ONBOARDING", "Auto advance triggered. Current item: ${binding.viewPager.currentItem}")

            if (binding.viewPager.currentItem == 0 && autoAdvanceEnabled) {
                if (!isFirstLaunch) {
                    // Быстрый старт - завершаем онбординг и переходим в приложение
                    Log.d("ONBOARDING", "Quick start completed, going to MainActivity")
                    completeOnboarding()
                } else {
                    // Первый запуск - переходим на следующую страницу
                    Log.d("ONBOARDING", "First launch, moving to next page")
                    binding.viewPager.currentItem = 1
                    autoAdvanceEnabled = false
                }
            }
        }
        handler.postDelayed(autoAdvanceRunnable!!, 5000) // 5 секунд
    }

    private fun setupDotsIndicator() {
        val dotsCount = viewPagerAdapter.itemCount

        if (dotsCount > 1) {
            binding.dotsContainer.visibility = android.view.View.VISIBLE
            binding.dotsContainer.removeAllViews()

            for (i in 0 until dotsCount) {
                val dot = android.view.View(this).apply {
                    layoutParams = android.widget.LinearLayout.LayoutParams(
                        24,
                        24
                    ).apply {
                        setMargins(8, 0, 8, 0)
                    }
                    background = getDrawable(R.drawable.dot_inactive)
                }
                binding.dotsContainer.addView(dot)
            }
        } else {
            binding.dotsContainer.visibility = android.view.View.GONE
        }

        updateDotsIndicator(0)
    }

    private fun updateDotsIndicator(position: Int) {
        if (viewPagerAdapter.itemCount > 1) {
            for (i in 0 until binding.dotsContainer.childCount) {
                val dot = binding.dotsContainer.getChildAt(i)
                dot.background = if (i == position) {
                    getDrawable(R.drawable.dot_active)
                } else {
                    getDrawable(R.drawable.dot_inactive)
                }
            }
        }
    }

    private fun setupButtons() {
        if (!isFirstLaunch) {
            // Быстрый старт - скрываем кнопки
            binding.buttonSkip.visibility = android.view.View.GONE
            binding.buttonNext.visibility = android.view.View.GONE
        } else {
            // Полный онбординг - показываем кнопки
            binding.buttonSkip.visibility = android.view.View.VISIBLE
            binding.buttonNext.visibility = android.view.View.VISIBLE

            binding.buttonSkip.setOnClickListener {
                completeOnboarding()
            }

            binding.buttonNext.setOnClickListener {
                val currentItem = binding.viewPager.currentItem
                if (currentItem < viewPagerAdapter.itemCount - 1) {
                    binding.viewPager.currentItem = currentItem + 1
                } else {
                    completeOnboarding()
                }
            }
        }
    }

    private fun updateButtons(position: Int) {
        if (isFirstLaunch && viewPagerAdapter.itemCount > 1) {
            if (position == viewPagerAdapter.itemCount - 1) {
                binding.buttonNext.text = "Начать"
            } else {
                binding.buttonNext.text = "Далее"
            }
        }
    }

    private fun completeOnboarding() {
        Log.d("ONBOARDING", "completeOnboarding called. isFirstLaunch: $isFirstLaunch")

        // Всегда устанавливаем флаг завершения онбординга
        sharedPreferences.edit().putBoolean(KEY_ONBOARDING_COMPLETED, true).apply()

        if (!isFirstLaunch) {
            // Быстрый старт - сразу в приложение
            Log.d("ONBOARDING", "Quick start: going to MainActivity")
            goToMainActivity()
        } else {
            // Первый запуск - показываем диалог авторизации
            Log.d("ONBOARDING", "First launch: showing auth dialog")
            showAuthChoiceDialog()
        }
    }

    private fun showAuthChoiceDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_auth_choice, null)

        val dialog = androidx.appcompat.app.AlertDialog.Builder(this)
            .setView(dialogView)
            .setCancelable(false)
            .create()

        dialogView.findViewById<MaterialButton>(R.id.buttonLogin).setOnClickListener {
            dialog.dismiss()
            // Устанавливаем флаг активности перед переходом на логин
            sharedPreferences.edit()
                .putBoolean("app_is_active", true)
                .apply()
            startActivity(LoginActivity.newIntent(this))
            finish()
        }

        dialogView.findViewById<MaterialButton>(R.id.buttonContinue).setOnClickListener {
            dialog.dismiss()
            goToMainActivity()
        }

        dialog.show()
    }

    private fun goToMainActivity() {
        // Устанавливаем флаги, что онбординг завершен и приложение активно
        sharedPreferences.edit()
            .putBoolean(KEY_ONBOARDING_COMPLETED, true)
            .putBoolean("just_completed_onboarding", true)
            .putBoolean("app_is_active", true)  // Флаг активности приложения
            .apply()

        val intent = MainActivity.newIntent(this).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        startActivity(intent)
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
    }
}