package com.sweaterweather.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val baseURL = "https://api.openweathermap.org"

object RetroClient {
    val retrofit: Retrofit =
        Retrofit.Builder().baseUrl(baseURL).addConverterFactory(GsonConverterFactory.create())
            .build()

    fun getClient() = retrofit
}