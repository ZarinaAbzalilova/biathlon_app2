package com.biathlonapp.ui.onboarding

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.biathlonapp.MainActivity
import com.biathlonapp.R
import com.biathlonapp.databinding.ActivityOnboardingBinding
import com.biathlonapp.ui.auth.LoginActivity
import com.google.android.material.button.MaterialButton

class OnboardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnboardingBinding
    private lateinit var viewPagerAdapter: OnboardingPagerAdapter
    private lateinit var sharedPreferences: SharedPreferences

    companion object {
        private const val PREFS_NAME = "onboarding_prefs"
        private const val KEY_ONBOARDING_COMPLETED = "onboarding_completed"

        fun newIntent(context: Context): Intent {
            return Intent(context, OnboardingActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

        setupViewPager()
        setupDotsIndicator()
        setupButtons()
    }

    private fun setupViewPager() {
        val onboardingItems = listOf(
            OnboardingItem(
                title = "Добро пожаловать в Биатлон!",
                description = "Ваш личный помощник в мире биатлона. Следите за любимыми спортсменами, результатами гонок и новостями.",
                imageRes = R.drawable.ic_biathlon_logo,  // Крупный логотип
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

        viewPagerAdapter = OnboardingPagerAdapter(onboardingItems)
        binding.viewPager.adapter = viewPagerAdapter

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                updateDotsIndicator(position)
                updateButtons(position)
            }
        })
    }

    private fun setupDotsIndicator() {
        val dotsCount = viewPagerAdapter.itemCount
        val dots = arrayOfNulls<android.view.View>(dotsCount)

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
            dots[i] = dot
        }

        updateDotsIndicator(0)
    }

    private fun updateDotsIndicator(position: Int) {
        for (i in 0 until binding.dotsContainer.childCount) {
            val dot = binding.dotsContainer.getChildAt(i)
            dot.background = if (i == position) {
                getDrawable(R.drawable.dot_active)
            } else {
                getDrawable(R.drawable.dot_inactive)
            }
        }
    }

    private fun setupButtons() {
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

    private fun updateButtons(position: Int) {
        if (position == viewPagerAdapter.itemCount - 1) {
            binding.buttonNext.text = "Начать"
        } else {
            binding.buttonNext.text = "Далее"
        }
    }

    private fun completeOnboarding() {
        sharedPreferences.edit().putBoolean(KEY_ONBOARDING_COMPLETED, true).apply()
        showAuthChoiceDialog()
    }

    private fun showAuthChoiceDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_auth_choice, null)

        val dialog = androidx.appcompat.app.AlertDialog.Builder(this)
            .setView(dialogView)
            .setCancelable(false)
            .create()

        dialogView.findViewById<MaterialButton>(R.id.buttonLogin).setOnClickListener {
            dialog.dismiss()  // ← Закрываем диалог перед переходом
            startActivity(LoginActivity.newIntent(this))
            finish()
        }

        dialogView.findViewById<MaterialButton>(R.id.buttonContinue).setOnClickListener {
            dialog.dismiss()  // ← Закрываем диалог перед переходом
            startActivity(MainActivity.newIntent(this))
            finish()
        }

        dialog.show()
    }
}