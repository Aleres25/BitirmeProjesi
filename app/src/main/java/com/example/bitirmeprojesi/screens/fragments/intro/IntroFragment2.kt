package com.example.bitirmeprojesi.screens.fragments.intro

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bitirmeprojesi.databinding.FragmentIntro2Binding


class IntroFragment2 : Fragment() {

    private lateinit var binding: FragmentIntro2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? { // Inflate the layout for this fragment
        binding = FragmentIntro2Binding.inflate(inflater, container, false)
        return binding.root
    }


}