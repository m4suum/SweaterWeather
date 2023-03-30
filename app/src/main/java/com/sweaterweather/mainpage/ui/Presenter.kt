package com.sweaterweather.mainpage.ui

import com.sweaterweather.mainpage.api.WeatherAPI
import com.sweaterweather.mainpage.api.models.CityWeather
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Presenter(private val api: WeatherAPI) : PresenterInterface {
    private var view: ViewInterface? = null
    override fun getCityWeather(cityName: String) {
        view?.showLoading(isLoading = true)
        api.getWeather(cityName).enqueue(object :Callback<CityWeather>{
            override fun onResponse(call: Call<CityWeather>, response: Response<CityWeather>) {
               if (response.isSuccessful && response.body() != null){
                   val data = response.body()
                   view?.showLoading(false)
                   data?.let {weather->
                       view?.changeTextColor(weather)
                       view?.showCityWeather(weather)
                   }

               }
            }

            override fun onFailure(call: Call<CityWeather>, t: Throwable) {
                view?.showLoading(false)
                view?.showError(t.message.toString())
            }

        })
    }


    fun attachView(newView: ViewInterface) {
        view = newView
    }

    fun detachView() {
        view = null
    }
}