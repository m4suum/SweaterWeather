package com.sweaterweather.mainpage.ui

import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import com.sweaterweather.databinding.ActivityMainBinding
import com.sweaterweather.mainpage.api.WeatherAPI
import com.sweaterweather.mainpage.api.models.CityWeather
import retrofit2.Call
import retrofit2.Response
import timber.log.Timber


class Presenter(private val api: WeatherAPI) : PresenterInterface {
    private lateinit var binding: ActivityMainBinding
    private var view: ViewInterface? = null
    override fun getCityWeather() {
        with(binding) {
            etEnterCity.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun afterTextChanged(s: Editable?) {
                    Handler(Looper.getMainLooper()).removeCallbacksAndMessages(null)
                    Handler(Looper.getMainLooper()).postDelayed({
                        val cityName = etEnterCity.text.toString()
                        api.getWeather(cityName)
                            .enqueue(object : retrofit2.Callback<CityWeather> {
                                override fun onResponse(
                                    call: Call<CityWeather>, response: Response<CityWeather>
                                ) {
                                    if (response.isSuccessful && response.body() != null) {
                                        val weatherData = response.body()
                                        if (weatherData != null) {
                                            view?.showCityWeather(weatherData)
                                        }
                                        Timber.i("______________${tvTemperature.text}")

                                    }
                                }

                                override fun onFailure(call: Call<CityWeather>, t: Throwable) {
                                    view?.showError(t.message.toString())
                                }
                            })
                    }, 900)
                }
            })
        }
    }
    fun attachView(mainActivity: MainActivity) {
        view = mainActivity
    }

    fun detachView() {
        view = null
    }
}