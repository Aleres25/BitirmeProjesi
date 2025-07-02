package com.example.bitirmeprojesi.screens.fragments.login_and_signup

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.bitirmeprojesi.R
import com.example.bitirmeprojesi.databinding.FragmentSignUpBinding
import com.example.bitirmeprojesi.screens.activities.FeedActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth


class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? { // Inflate the layout for this fragment
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        auth=Firebase.auth
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userId = auth.currentUser?.uid
        if (userId != null) {
            Log.e("SignUpFragmentLogları", userId)
            findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)
        } else {
            Log.e("SignUpFragmentLogları", "Uid Boş")
        }

        binding.gotoLogin.setOnClickListener {
            val navOptions = NavOptions.Builder()
                .setEnterAnim(R.anim.slide_in_right) // loginFragment sağdan girsin
                .setExitAnim(R.anim.slide_out_left)  // signupFragment sola çıksın
                .build()

            findNavController().navigate(R.id.action_signUpFragment_to_loginFragment, null, navOptions)
        }


       /* auth.createUserWithEmailAndPassword("asd@gmail.com","1252423").addOnSuccessListener({
            Toast.makeText(requireContext(),"Tebrikler",Toast.LENGTH_SHORT).show()
        }).addOnFailureListener{
            Log.e("Hata",it.localizedMessage)
            Toast.makeText(requireContext(),it.localizedMessage,Toast.LENGTH_SHORT).show()
        }


    }*/
}}