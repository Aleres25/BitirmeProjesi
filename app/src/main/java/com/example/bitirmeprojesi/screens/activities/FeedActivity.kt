package com.example.bitirmeprojesi.screens.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.bitirmeprojesi.R
import com.example.bitirmeprojesi.databinding.ActivityFeedBinding

class FeedActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFeedBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFeedBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}