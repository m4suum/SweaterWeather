package com.sweaterweather.mainpage.api

import com.sweaterweather.mainpage.api.models.CityWeather
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {
    @GET("data/2.5/weather?appid=61d4217a1cf164674df0b8e4dfd8f078&units=metric")
    fun getWeather(@Query("q") cityName: String): retrofit2.Call<CityWeather>
}