package com.sweaterweather

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.viewbinding.BuildConfig
import com.sweaterweather.databinding.ActivityMainBinding
import com.sweaterweather.retrofit.WeatherAPI
import com.sweaterweather.test.ProductApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    private val baseURL = "https://dummyjson.com"
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Timber.plant(Timber.DebugTree())

        var bsum = 4 + 2

        with(binding) {

            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder().addInterceptor(interceptor).build()


            val retrofit =
                Retrofit.Builder().baseUrl(baseURL).client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            val productApi = retrofit.create(ProductApi::class.java)

//            val weatherAPI = retrofit.create(WeatherAPI::class.java)
            buttonGetTemperature.setOnClickListener {
                Timber.i("______$bsum")
                CoroutineScope(Dispatchers.IO).launch {
                    val id = etEnterId.text.toString()
                    val product = productApi.getProductById(id.toInt())
                    runOnUiThread {
                        tvTemperature.text = product.title
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