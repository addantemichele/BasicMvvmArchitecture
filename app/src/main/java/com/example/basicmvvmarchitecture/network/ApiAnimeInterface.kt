package com.example.basicmvvmarchitecture.network

import com.example.basicmvvmarchitecture.model.AnimeModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiAnimeInterface {
    @GET("63b2cde601a72b59f23e7710?meta=false")
    fun getAllAnime(): Call<List<AnimeModel>>


    @POST("63b2dc7b15ab31599e2a381f?meta=false")
    fun insertAnime(@Body animeModel: AnimeModel) : Call<AnimeModel>


}