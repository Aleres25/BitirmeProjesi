package com.example.bitirmeprojesi.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bitirmeprojesi.entitiy.CartItem
import com.example.bitirmeprojesi.repository.CartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
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
                val rawItems = repo.getCartItems(username)

                val groupedItems = rawItems.groupBy { it.ad }.map { (_, list) ->
                    list.reduce { acc, item ->
                        acc.copy(siparisAdeti = acc.siparisAdeti + item.siparisAdeti)
                    }
                }
                cartItems.value = groupedItems
            } catch (e: Exception) {
                cartItems.value = emptyList()
            }
        }
    }

    fun deleteCartItem(sepetId: Int, kullaniciAdi: String) {
        viewModelScope.launch {
            val response = repo.deleteCartItem(sepetId, kullaniciAdi)
            println("Silme sonucu: ${response.message}")
            loadCart(kullaniciAdi)
        }
    }

    fun updateQuantityOrReplace(item: CartItem, newQty: Int) {
        viewModelScope.launch {
            val allItems = repo.getCartItems(item.kullaniciAdi)
            allItems.filter { it.ad == item.ad }.forEach {
                repo.deleteCartItem(it.sepetId, it.kullaniciAdi)
            }
            delay(200)
            if (newQty > 0) {
                repo.addToCart(
                    ad = item.ad,
                    resim = item.resim,
                    kategori = item.kategori,
                    fiyat = item.fiyat,
                    marka = item.marka,
                    siparisAdeti = newQty,
                    kullaniciAdi = item.kullaniciAdi
                )
            }
            loadCart(item.kullaniciAdi)
        }
    }


}
