package com.example.bitirmeprojesi.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bitirmeprojesi.repository.InternetCheckRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InternetCheckViewModel @Inject constructor(
    private val repo: InternetCheckRepository
) : ViewModel() {

    val isConnected = MutableLiveData<Boolean>()

    fun checkConnection() {
        viewModelScope.launch {
            try {
                val result = repo.checkInternet()
                isConnected.value = result.answer
            } catch (e: Exception) {
                isConnected.value = false
            }
        }
    }
}