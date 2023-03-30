package com.sweaterweather.mainpage.ui

import android.annotation.SuppressLint
import android.content.res.Resources
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import com.sweaterweather.R
import com.sweaterweather.databinding.ActivityMainBinding
import com.sweaterweather.mainpage.api.RetroClient
import com.sweaterweather.mainpage.api.WeatherAPI
import com.sweaterweather.mainpage.api.models.CityWeather
import timber.log.Timber

private const val CITY_NAME_LENGTH = 2

class MainActivity : AppCompatActivity(), ViewInterface {
    private val api = RetroClient.getClient().create(WeatherAPI::class.java)
    private val presenter: Presenter = Presenter(api)
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter.attachView(this@MainActivity)

        binding.editTextEnterCity.doAfterTextChanged { cityName ->
            if (!cityName.isNullOrEmpty() && cityName.length > CITY_NAME_LENGTH)
                presenter.getCityWeather(cityName.toString())
        }

    }

    @SuppressLint("SetTextI18n")
    override fun showCityWeather(data: CityWeather) {
        with(binding) {
            textViewCityName.text =
                data.name?.replaceFirstChar { it.uppercase() }
            textViewTemperature.text = tempDescription(data)

            textViewDescription.text =
                data.weather?.get(0)?.description.toString()
            Timber.i("______________${textViewTemperature.text}")
        }
    }

    override fun showError(message: String) {
        Timber.e("---ERROR -> $message")
    }

    override fun showLoading(isLoading: Boolean) {
        binding.progress.isVisible = isLoading
    }

    override fun changeTextColor(data: CityWeather) {
        with(binding) {
            when (data.main?.temp?.toInt()) {
                in Int.MIN_VALUE..0 -> textViewTemperature.setTextColor(
                    ContextCompat.getColor(
                        this@MainActivity,
                        R.color.dark_blue
                    )
                )

                in 1..9 -> textViewTemperature.setTextColor(
                    ContextCompat.getColor(
                        this@MainActivity,
                        R.color.green
                    )
                )

                in 10..19 -> textViewTemperature.setTextColor(
                    ContextCompat.getColor(
                        this@MainActivity,
                        R.color.yellow
                    )
                )

                in 20..Int.MAX_VALUE -> textViewTemperature.setTextColor(
                    ContextCompat.getColor(
                        this@MainActivity,
                        R.color.red
                    )
                )
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    private fun tempDescription(data: CityWeather):String{
        val tempData = data.main?.temp
        val descriptionOfTemp = getString(R.string.celsius)
        return buildString {
            append(tempData)
                .append(descriptionOfTemp)
        }
    }
}
