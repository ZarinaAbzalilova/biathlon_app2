package com.biathlonapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.biathlonapp.data.repository.AuthRepository
import com.biathlonapp.databinding.ActivityMainBinding
import com.biathlonapp.ui.adapters.ViewPagerAdapter
import com.biathlonapp.ui.auth.LoginActivity
import com.biathlonapp.ui.settings.SettingsActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private lateinit var authRepository: AuthRepository

    companion object {
        fun newIntent(context: Context) = Intent(context, MainActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        authRepository = AuthRepository(this)

        // Проверяем авторизацию
        if (!authRepository.isLoggedIn()) {
            startActivity(LoginActivity.newIntent(this))
            finish()
            return
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.buttonSettings.setOnClickListener {
            startActivity(SettingsActivity.newIntent(this))
        }
        setupToolbar()
        setupViewPager()
        setupBottomNavigation()
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