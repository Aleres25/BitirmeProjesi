package com.example.bitirmeprojesi.viewmodel

import androidx.lifecycle.ViewModel
import com.example.bitirmeprojesi.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repo: AuthRepository
) : ViewModel() {

    fun register(email: String, password: String, name: String, onResult: (Boolean, String?) -> Unit) {
        repo.registerUser(email, password, name, onResult)
    }
}