package com.biathlonapp.ui.adapters


import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.biathlonapp.ui.athletes.AthletesFragment
import com.biathlonapp.ui.news.NewsFragment

class ViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = 2 // Новости и Спортсмены

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> NewsFragment()
            1 -> AthletesFragment()
            else -> throw IllegalArgumentException("Invalid position $position")
        }
    }
}