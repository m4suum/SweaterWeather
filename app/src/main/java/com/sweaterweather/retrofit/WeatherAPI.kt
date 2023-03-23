package com.sweaterweather.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface WeatherAPI {
    @GET("continuation of the link/{city}")
    suspend fun getTemperature (@Path("city") city:String): Weather


    /**Второй вариант**************************************************/
//    companion object {
//
//        private const val BASE_URL = "https://openweathermap.org"
//
//        fun create() : WeatherAPI {
//
//            val retrofit = Retrofit.Builder()
//                .addConverterFactory(GsonConverterFactory.create())
//                .baseUrl(BASE_URL)
//                .build()
//            return retrofit.create(WeatherAPI::class.java)
//
//        }
//    }
    /*******************************************************************/

}