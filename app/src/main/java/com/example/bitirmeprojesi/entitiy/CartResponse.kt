package com.example.bitirmeprojesi.entitiy

data class CartResponse(
    val urunler_sepeti: List<CartItem>,
    val success: Int
)
