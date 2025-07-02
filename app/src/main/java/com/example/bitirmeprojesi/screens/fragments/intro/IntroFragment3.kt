package com.example.bitirmeprojesi.screens.fragments.intro

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bitirmeprojesi.R
import com.example.bitirmeprojesi.databinding.FragmentIntro3Binding
import com.example.bitirmeprojesi.screens.activities.FeedActivity


class IntroFragment3 : Fragment() {

    private lateinit var binding: FragmentIntro3Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? { // Inflate the layout for this fragment
        binding = FragmentIntro3Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.animationView.setOnClickListener {
            Intent(requireActivity(),FeedActivity::class.java).apply {
                startActivity(this)
            }
        }
    }
}