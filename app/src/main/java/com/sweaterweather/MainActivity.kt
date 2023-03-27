package com.sweaterweather

import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import com.sweaterweather.databinding.ActivityMainBinding
import com.sweaterweather.retrofit.CityWeather
import com.sweaterweather.retrofit.RetroClient
import com.sweaterweather.retrofit.WeatherAPI
import retrofit2.Call
import retrofit2.Response
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val weatherAPI = RetroClient.getClient().create(WeatherAPI::class.java)
    private val handler = Handler()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Timber.plant(Timber.DebugTree())

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
                    handler.removeCallbacksAndMessages(null)
                    handler.postDelayed({
                        val cityName = etEnterCity.text.toString()
                        weatherAPI.getWeather(cityName)
                            .enqueue(object : retrofit2.Callback<CityWeather> {
                                override fun onResponse(
                                    call: Call<CityWeather>, response: Response<CityWeather>
                                ) {
                                    if (response.isSuccessful && response.body() != null) {
                                        val weatherData = response.body()
                                        tvCityName.text = cityName
                                        var temperature = weatherData?.main?.temp?.toInt()
                                        when (temperature) {
                                            in Int.MIN_VALUE..0 -> {
                                                tvTemperature.setTextColor(getColor(R.color.dark_blue))
                                            }
                                            in 1..9 -> {
                                                tvTemperature.setTextColor(getColor(R.color.green))
                                            }
                                            in 10..19 -> {
                                                tvTemperature.setTextColor(getColor(R.color.yellow))
                                            }
                                            in 20..Int.MAX_VALUE -> {
                                                tvTemperature.setTextColor(getColor(R.color.red))
                                            }
                                        }
                                        tvTemperature.text =
                                            "${weatherData?.main?.temp.toString()} °C"
                                        tvDescription.text =
                                            weatherData?.weather?.get(0)?.description.toString()
                                        Timber.i("______________${tvTemperature.text}")

                                    }
                                }

                                override fun onFailure(call: Call<CityWeather>, t: Throwable) {
                                    Timber.e("---ERROR -> ${t.message.toString()}")
                                }
                            })
                    }, 2000)
                }
            })
        }
    }
}

