package com.example.bitirmeprojesi.datasource

import com.example.bitirmeprojesi.entitiy.CartItem
import com.example.bitirmeprojesi.retrofit.CartDao

class CartDataSource(private val dao: CartDao) {
    suspend fun addToCart(
        ad: String,
        resim: String,
        kategori: String,
        fiyat: Int,
        marka: String,
        siparisAdeti: Int,
        kullaniciAdi: String
    ) = dao.addToCart(ad, resim, kategori, fiyat, marka, siparisAdeti, kullaniciAdi)

    suspend fun getCartItems(kullaniciAdi: String): List<CartItem> {
        return dao.getCartItems(kullaniciAdi).urunler_sepeti
    }
    suspend fun deleteFromCart(sepetId: Int, kullaniciAdi: String) =
        dao.deleteFromCart(sepetId, kullaniciAdi)
}