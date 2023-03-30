package com.sweaterweather.mainpage.ui

import android.content.res.Resources
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sweaterweather.R
import com.sweaterweather.databinding.ActivityMainBinding
import com.sweaterweather.mainpage.api.RetroClient
import com.sweaterweather.mainpage.api.WeatherAPI
import com.sweaterweather.mainpage.api.models.CityWeather
import timber.log.Timber

class MainActivity : AppCompatActivity(), ViewInterface {
    private val api = RetroClient.getClient().create(WeatherAPI::class.java)
    private val presenter: Presenter = Presenter(api)
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.attachView(this)
        presenter.getCityWeather()

    }

    override fun showCityWeather(data: CityWeather) {
        with(binding) {
            tvCityName.text =
                data.name?.replaceFirstChar { it.uppercase() }
            when (data.main?.temp?.toInt()) {
                in Int.MIN_VALUE..0 -> {
                    tvTemperature.setTextColor(
                        Resources.getSystem()
                            .getColor(R.color.dark_blue)
                    )
                }
                in 1..9 -> {
                    tvTemperature.setTextColor(
                        Resources.getSystem().getColor(R.color.green)
                    )
                }
                in 10..19 -> {
                    tvTemperature.setTextColor(
                        Resources.getSystem().getColor(R.color.yellow)
                    )
                }
                in 20..Int.MAX_VALUE -> {
                    tvTemperature.setTextColor(
                        Resources.getSystem().getColor(R.color.red)
                    )
                }
            }
            val temperature =
                data.main?.temp.toString() + Resources.getSystem()
                    .getString(R.string.celsius)
            tvTemperature.text = temperature
            tvDescription.text =
                data.weather?.get(0)?.description.toString()
        }
    }

    override fun showError(message: String) {
        Timber.e("---ERROR -> $message")
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }
}


