package com.example.cinemate.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.cinemate.fragment.*

class MainFragmentStateAdapter(fragmentActivity: FragmentActivity, private val fragmentCount: Int) : FragmentStateAdapter(fragmentActivity) {
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> HomeFragment()
            1 -> PeopleFragment()
            2 -> SearchFragment()
            3 -> MypageFragment()
            else -> HomeFragment()
        }
    }

    override fun getItemCount(): Int = fragmentCount
}
