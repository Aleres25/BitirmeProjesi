package com.example.bitirmeprojesi.entitiy

data class CartItem(
    val sepetId: Int,
    val ad: String,
    val resim: String,
    val kategori: String,
    val fiyat: Int,
    val marka: String,
    val siparisAdeti: Int,
    val kullaniciAdi: String
)
