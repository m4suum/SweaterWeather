package com.sweaterweather.mainpage.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val baseURL = "https://api.openweathermap.org"

object RetroClient {
    fun getClient() = Retrofit.Builder()
        .baseUrl(baseURL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}