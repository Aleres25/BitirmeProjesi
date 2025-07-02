package com.example.bitirmeprojesi.di

import com.example.bitirmeprojesi.datasource.InternetCheckDataSource
import com.example.bitirmeprojesi.repository.InternetCheckRepository
import com.example.bitirmeprojesi.retrofit.InternetCheckDao
import com.example.bitirmeprojesi.utils.RetrofitClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideInternetCheckDao(): InternetCheckDao {
        return RetrofitClient.getClient("https://raw.githubusercontent.com/Aleres25/internet_check_api/main/")
            .create(InternetCheckDao::class.java)
    }

    @Provides
    @Singleton
    fun provideInternetCheckDataSource(dao: InternetCheckDao): InternetCheckDataSource {
        return InternetCheckDataSource(dao)
    }

    @Provides
    @Singleton
    fun provideInternetCheckRepository(ds: InternetCheckDataSource): InternetCheckRepository {
        return InternetCheckRepository(ds)
    }
}