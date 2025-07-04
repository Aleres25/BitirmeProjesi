package com.example.bitirmeprojesi.repository

import com.example.bitirmeprojesi.datasource.ProductDataSource

class ProductRepository(private val ds: ProductDataSource) {
    suspend fun getAllProducts() = ds.getAllProducts()
}