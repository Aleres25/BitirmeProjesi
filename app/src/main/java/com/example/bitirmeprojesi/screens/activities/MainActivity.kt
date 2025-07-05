package com.example.bitirmeprojesi.screens.activities

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.bitirmeprojesi.adapter.ViewPagerAdapterForIntro
import com.example.bitirmeprojesi.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ViewPagerAdapterForIntro
    private val timer = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding){
            adapter = ViewPagerAdapterForIntro(this@MainActivity)
            viewPager.adapter = adapter
            dotsInducator.attachTo(binding.viewPager)


            startAutoSwiping()
            binding.viewPager.registerOnPageChangeCallback(onPageChangeCallBack)
        }


    }

    private fun startAutoSwiping() {
        autoSwipeHandler.postDelayed(autoSwipeRunnable, 3000L)
    }

    private fun stopAutoSwiping() {
        autoSwipeHandler.removeCallbacks(autoSwipeRunnable)
    }

    private val autoSwipeHandler = Handler(Looper.getMainLooper())

    private val autoSwipeRunnable = object : Runnable {
        override fun run() {
            val currentItem = binding.viewPager.currentItem
            val nextItem = (currentItem + 1) % (binding.viewPager.adapter?.itemCount ?: 1)
            binding.viewPager.setCurrentItem(nextItem, true)
            autoSwipeHandler.postDelayed(this, 3000L)
        }
    }
    private val onPageChangeCallBack = object : ViewPager2.OnPageChangeCallback() {

        override fun onPageScrollStateChanged(state: Int) {
            super.onPageScrollStateChanged(state)

            when (state) {
                ViewPager2.SCROLL_STATE_DRAGGING -> {
                    stopAutoSwiping()
                }

                ViewPager2.SCROLL_STATE_IDLE -> {
                    startAutoSwiping()
                }
            }
        }


        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            stopAutoSwiping()
            startAutoSwiping()
        }
    }
}