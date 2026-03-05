package com.biathlonapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.biathlonapp.databinding.ActivityMainBinding
import com.biathlonapp.ui.adapters.ViewPagerAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewPagerAdapter: ViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewPager()
        setupBottomNavigation()
    }

    private fun setupViewPager() {
        viewPagerAdapter = ViewPagerAdapter(this)
        binding.viewPager.apply {
            adapter = viewPagerAdapter
            // Отключаем свайп между страницами, чтобы навигация была только через нижнее меню
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