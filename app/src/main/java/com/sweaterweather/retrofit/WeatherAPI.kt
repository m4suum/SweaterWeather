package com.sweaterweather.retrofit

import retrofit2.http.GET
import retrofit2.http.Path

interface WeatherAPI {
    @GET("data/2.5/weather?q={city name}&appid=61d4217a1cf164674df0b8e4dfd8f078")
    suspend fun getTemperature (@Path("city name") cityName:String): Weather
}