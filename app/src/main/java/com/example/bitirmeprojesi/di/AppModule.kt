package com.example.bitirmeprojesi.di

import com.example.bitirmeprojesi.datasource.InternetCheckDataSource
import com.example.bitirmeprojesi.datasource.ProductDataSource
import com.example.bitirmeprojesi.repository.InternetCheckRepository
import com.example.bitirmeprojesi.repository.ProductRepository
import com.example.bitirmeprojesi.retrofit.InternetCheckDao
import com.example.bitirmeprojesi.retrofit.ProductDao
import com.example.bitirmeprojesi.utils.RetrofitClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
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

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()


    @Provides
    @Singleton
    fun provideFirebaseDatabase(): FirebaseDatabase = FirebaseDatabase.getInstance()


    @Provides
    @Singleton
    fun provideProductDao(): ProductDao {
        return RetrofitClient.getClient("http://kasimadalan.pe.hu/")
            .create(ProductDao::class.java)
    }

    @Provides
    @Singleton
    fun provideProductDataSource(productDao: ProductDao): ProductDataSource {
        return ProductDataSource(productDao)
    }

    @Provides
    @Singleton
    fun provideProductRepository(ds: ProductDataSource): ProductRepository {
        return ProductRepository(ds)
    }

}