package com.example.githubprofilesapp.model.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "https://api.github.com/"

    private fun getRetrofitInstance():Retrofit{
    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    }
    val networkService: NetworkService by lazy {
        getRetrofitInstance().create(NetworkService::class.java)
    }
}