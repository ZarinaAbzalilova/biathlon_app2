package com.biathlonapp.ui.adapters


import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.biathlonapp.ui.athletes.AthletesFragment
import com.biathlonapp.ui.calendar.CalendarFragment
import com.biathlonapp.ui.favorites.FavoritesFragment
import com.biathlonapp.ui.news.NewsFragment
import com.biathlonapp.ui.search.SearchFragment
import com.biathlonapp.ui.team.TeamCategoriesFragment

class ViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = 5 // Новости и Спортсмены

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> NewsFragment()
            1 -> TeamCategoriesFragment()
            2 -> CalendarFragment()
            3 -> FavoritesFragment()
            4 -> SearchFragment()
            else -> throw IllegalArgumentException("Invalid position $position")
        }
    }
}