package com.example.bitirmeprojesi.entitiy

import java.io.Serializable


data class Product(
    val id: Int,
    val ad: String,
    val resim: String,
    val kategori: String,
    val fiyat: Int,
    val marka: String
): Serializable
