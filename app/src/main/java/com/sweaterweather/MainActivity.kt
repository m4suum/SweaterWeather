package com.sweaterweather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sweaterweather.databinding.ActivityMainBinding
import com.sweaterweather.retrofit.WeatherAPI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    private val baseURL = "https://api.openweathermap.org"
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Timber.plant(Timber.DebugTree())

        with(binding) {

            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

            val retrofit =
                Retrofit.Builder().baseUrl(baseURL).client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            val weatherAPI = retrofit.create(WeatherAPI::class.java)

            buttonGetTemperature.setOnClickListener {
                CoroutineScope(Dispatchers.IO).launch {
                    val city = etEnterId.text.toString()
                    val temperature = weatherAPI.getTemperature(city)
                    runOnUiThread {
                        tvTemperature.text = temperature.weather.toString()
                    }
                }
            }


            /**Второй вариант
            CoroutineScope(Dispatchers.IO).launch {
            val weatherAPI = WeatherAPI.create().getTemperature("Bishkek")
            runOnUiThread {
            tvTemperature.text = weatherAPI.temperature
            }
            }
             */


        }
    }
}