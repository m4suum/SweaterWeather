package com.sweaterweather.mainpage.ui

import com.sweaterweather.mainpage.api.models.CityWeather

interface ViewInterface {
    fun showCityWeather (data:CityWeather)
    fun showError (message: String)

}