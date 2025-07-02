package com.example.bitirmeprojesi.retrofit

import com.example.bitirmeprojesi.entitiy.InternetCheckResponse
import retrofit2.http.GET

interface InternetCheckDao {

    @GET("internet_check_api/internet_check.json")
    suspend fun checkInternet(): InternetCheckResponse
}