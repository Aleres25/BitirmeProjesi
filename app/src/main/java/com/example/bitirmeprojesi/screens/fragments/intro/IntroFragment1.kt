package com.example.bitirmeprojesi.screens.fragments.intro

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.bitirmeprojesi.databinding.FragmentIntro1Binding
import com.example.bitirmeprojesi.screens.activities.FeedActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth


class IntroFragment1 : Fragment() {

    private lateinit var binding: FragmentIntro1Binding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? { // Inflate the layout for this fragment
        binding = FragmentIntro1Binding.inflate(inflater, container, false)
        auth = Firebase.auth
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userId = auth.currentUser?.uid
        if (userId != null) {
            Intent(requireContext(), FeedActivity::class.java).apply {
                startActivity(this)
            }
        } else {
            Log.e("IntroFragment1Logları", "Uid Boş")
        }

    }
}