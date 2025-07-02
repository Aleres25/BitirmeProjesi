package com.example.bitirmeprojesi.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.bitirmeprojesi.screens.fragments.intro.IntroFragment1
import com.example.bitirmeprojesi.screens.fragments.intro.IntroFragment2
import com.example.bitirmeprojesi.screens.fragments.intro.IntroFragment3

class ViewPagerAdapterForIntro(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    val fragmentList = listOf(IntroFragment1(), IntroFragment2(), IntroFragment3())

    override fun getItemCount(): Int = fragmentList.size

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }
}