package com.example.bitirmeprojesi.repository

import com.example.bitirmeprojesi.datasource.InternetCheckDataSource
import jakarta.inject.Inject

class InternetCheckRepository @Inject constructor(
    private val dataSource: InternetCheckDataSource
) {
    suspend fun checkInternet() = dataSource.checkInternet()
}