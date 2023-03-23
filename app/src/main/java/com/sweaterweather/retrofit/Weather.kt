package com.sweaterweather.retrofit

data class Weather(
    val coord: Any? = Any(),
    val weather: ArrayList<Any> = arrayListOf(),
    val base: String?,
    val main: Any? = Any(),
    val visibility: Int?,
    val wind: Any? = Any(),
    val clouds: Any? = Any(),
    val dt: Int?,
    val sys: Any? = Any(),
    val timezone: Int?,
    val id: Int?,
    val name: String?,
    val cod: Int?
)
