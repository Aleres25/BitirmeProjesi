package com.example.bitirmeprojesi.screens.fragments.login_and_signup

import android.os.Bundle
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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding
    private lateinit var auth: FirebaseAuth

    // 🔹 ViewModel'i Hilt ile alıyoruz
    private val internetCheckViewModel: InternetCheckViewModel by viewModels()

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

        // 🔹 Kullanıcı daha önce giriş yaptıysa login'e yönlendir
        val userId = auth.currentUser?.uid
        if (userId != null) {
            Log.e("SignUpFragmentLogları", "Zaten giriş yapılmış: $userId")
            findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)
        }

        // 🔹 İnternet kontrolü
        internetCheckViewModel.checkConnection()
        internetCheckViewModel.isConnected.observe(viewLifecycleOwner) { isConnected ->
            if (isConnected) {
                Toast.makeText(requireContext(), "İnternet bağlantısı var", Toast.LENGTH_SHORT).show()
                // → Burada registerUser() fonksiyonunu çağır
            } else {
                Toast.makeText(requireContext(), "İnternet bağlantısı yok", Toast.LENGTH_SHORT).show()
            }
        }

        // 🔹 Giriş sayfasına geç
        binding.gotoLogin.setOnClickListener {
            val navOptions = NavOptions.Builder()
                .setEnterAnim(R.anim.slide_in_right)
                .setExitAnim(R.anim.slide_out_left)
                .build()
            findNavController().navigate(R.id.action_signUpFragment_to_loginFragment, null, navOptions)
        }
    }
}
