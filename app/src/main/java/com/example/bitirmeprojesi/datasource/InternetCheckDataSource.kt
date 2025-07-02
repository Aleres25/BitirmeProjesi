package com.example.bitirmeprojesi.datasource

import com.example.bitirmeprojesi.retrofit.InternetCheckDao
import jakarta.inject.Inject

class InternetCheckDataSource @Inject constructor(
    private val dao: InternetCheckDao
) {
    suspend fun checkInternet() = dao.checkInternet()
}