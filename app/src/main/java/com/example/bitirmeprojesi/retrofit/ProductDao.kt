package com.example.bitirmeprojesi.retrofit

import com.example.bitirmeprojesi.entitiy.ProductResponse
import retrofit2.http.GET

interface ProductDao {
    @GET("urunler/tumUrunleriGetir.php")
    suspend fun getAllProducts(): ProductResponse

}