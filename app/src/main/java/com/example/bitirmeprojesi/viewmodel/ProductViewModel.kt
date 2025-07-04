package com.example.bitirmeprojesi.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bitirmeprojesi.entitiy.Product
import com.example.bitirmeprojesi.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(private val repo: ProductRepository) : ViewModel() {

    val productList = MutableLiveData<List<Product>>()

    fun loadProducts() {
        viewModelScope.launch {
            try {
                val products = repo.getAllProducts()
                productList.value = products
                Log.e("ProductViewModel", "Gelen veri sayısı: ${products.size}")
            } catch (e: Exception) {
                Log.e("ProductViewModel", "Veri çekme hatası: ${e.localizedMessage}")
                productList.value = emptyList()
            }
        }
    }
}
