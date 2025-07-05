package com.example.bitirmeprojesi.retrofit

import com.example.bitirmeprojesi.entitiy.CRUDResponse
import com.example.bitirmeprojesi.entitiy.CartResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface CartDao {
    @POST("urunler/sepeteUrunEkle.php")
    @FormUrlEncoded
    suspend fun addToCart(
        @Field("ad") ad: String,
        @Field("resim") resim: String,
        @Field("kategori") kategori: String,
        @Field("fiyat") fiyat: Int,
        @Field("marka") marka: String,
        @Field("siparisAdeti") siparisAdeti: Int,
        @Field("kullaniciAdi") kullaniciAdi: String
    ): CRUDResponse

    @FormUrlEncoded
    @POST("urunler/sepettekiUrunleriGetir.php")
    suspend fun getCartItems(
        @Field("kullaniciAdi") kullaniciAdi: String
    ): CartResponse

    @POST("urunler/sepettenUrunSil.php")
    @FormUrlEncoded
    suspend fun deleteFromCart(
        @Field("sepetId") sepetId: Int,
        @Field("kullaniciAdi") kullaniciAdi: String
    ): CRUDResponse
}