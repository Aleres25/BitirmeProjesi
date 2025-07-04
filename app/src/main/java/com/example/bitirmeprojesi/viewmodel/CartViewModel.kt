package com.example.bitirmeprojesi.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bitirmeprojesi.entitiy.CartItem
import com.example.bitirmeprojesi.repository.CartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(private val repo: CartRepository) : ViewModel() {

    val cartItems = MutableLiveData<List<CartItem>>()


    fun addToCart(ad: String, resim: String, kategori: String, fiyat: Int, marka: String, siparisAdeti: Int, kullaniciAdi: String) {
        viewModelScope.launch {
            val response = repo.addToCart(ad, resim, kategori, fiyat, marka, siparisAdeti, kullaniciAdi)
            println("Sepete ekleme sonucu: ${response.message}")
        }
    }

    fun loadCart(username: String) {
        viewModelScope.launch {
            try {
                cartItems.value = repo.getCartItems(username)
            } catch (e: Exception) {
                cartItems.value = emptyList()
            }
        }
    }
}