package com.example.bitirmeprojesi.screens.fragments.login_and_signup

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.bitirmeprojesi.R
import com.example.bitirmeprojesi.databinding.FragmentSignUpBinding
import com.example.bitirmeprojesi.viewmodel.InternetCheckViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import androidx.core.graphics.toColorInt
import com.example.bitirmeprojesi.viewmodel.AuthViewModel

@AndroidEntryPoint
class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding
    private lateinit var auth: FirebaseAuth

    private val internetCheckViewModel: InternetCheckViewModel by viewModels()
    private val authViewModel: AuthViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        auth = Firebase.auth
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val userId = auth.currentUser?.uid
        if (userId != null) {
            Log.e("SignUpFragmentLogları", "Zaten giriş yapılmış: $userId")
            findNavController().navigate(R.id.action_signUpFragment_to_feedFragment)
        }


        internetCheckViewModel.checkConnection()
        internetCheckViewModel.isConnected.observe(viewLifecycleOwner) { isConnected ->
            if (isConnected) {
                Log.e("answer",isConnected.toString())
            } else {
                Snackbar.make(requireView(), "İnternet bağlantısı yok", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Ayarlar") {
                        val intent = Intent(Settings.ACTION_SETTINGS)
                        startActivity(intent)
                    }
                    .setBackgroundTint("#222222".toColorInt())
                    .setTextColor(Color.WHITE)
                    .setActionTextColor(Color.RED)
                    .show()
            }
        }
        with(binding){
            signupButton.setOnClickListener {
                val email = emailInput.text.toString().trim()
                val password = passwordInput.text.toString().trim()
                val name = nameInput.text.toString().trim()

                authViewModel.register(email, password, name) { success, errorMsg ->
                    if (success) {
                        Toast.makeText(requireContext(), "Kayıt başarılı", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)
                    } else {
                        Toast.makeText(requireContext(), "Hata: $errorMsg", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            gotoLogin.setOnClickListener {
                val navOptions = NavOptions.Builder()
                    .setEnterAnim(R.anim.slide_in_right)
                    .setExitAnim(R.anim.slide_out_left)
                    .build()
                findNavController().navigate(R.id.action_signUpFragment_to_loginFragment, null, navOptions)
            }
        }
        }
}
