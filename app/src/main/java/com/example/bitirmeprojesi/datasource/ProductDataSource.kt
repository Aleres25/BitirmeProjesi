package com.example.bitirmeprojesi.datasource

import com.example.bitirmeprojesi.retrofit.ProductDao

class ProductDataSource(private val dao: ProductDao) {
    suspend fun getAllProducts() = dao.getAllProducts().urunler
}