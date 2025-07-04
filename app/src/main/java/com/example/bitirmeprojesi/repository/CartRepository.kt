package com.example.bitirmeprojesi.repository

import com.example.bitirmeprojesi.datasource.CartDataSource
import com.example.bitirmeprojesi.entitiy.CartItem


class CartRepository(private val ds: CartDataSource) {

    suspend fun addToCart(
        ad: String,
        resim: String,
        kategori: String,
        fiyat: Int,
        marka: String,
        siparisAdeti: Int,
        kullaniciAdi: String
    ) = ds.addToCart(ad, resim, kategori, fiyat, marka, siparisAdeti, kullaniciAdi)

    suspend fun getCartItems(kullaniciAdi: String): List<CartItem> {
        return ds.getCartItems(kullaniciAdi)
    }
}