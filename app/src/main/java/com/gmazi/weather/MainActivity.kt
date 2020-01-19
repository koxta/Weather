package com.gmazi.weather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.gmazi.weather.models.CurrentWeatherResponse
import com.gmazi.weather.services.APIWeather
import com.gmazi.weather.services.RetrofitEventListener
import com.gmazi.weather.services.WeatherClient
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import kotlin.math.log

class MainActivity : AppCompatActivity() {

    val apiKey = "d37c7266c862c14b319e92c8f9c6e4f3"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("asd","oncreate")
        CallWeatherData()
    }

    internal fun CallWeatherData() {
        Log.d("asd","call ")
        WeatherClient.instance.getWeather( object : RetrofitEventListener {
            override  fun onSuccess(call: Call<*>, response: Any) {
                Log.d("asd",response.toString())
                if (response is CurrentWeatherResponse) {
                    Log.d("asd", "-----" + response.main?.temp)
                    Log.d("asd",response.toString())
                    val currentCels = kalvinToCelsius(response.main.temp);

                    main_temperature.text=currentCels.toString()+"°c"
                }
                else{
                    Log.d("asd","not weather")
                }
            }

            override fun onError(call: Call<*>, t: Throwable) {
                Log.d("asd",t.toString())
                Log.d("asd","asdasdasdasd")
            }
        })
    }

    fun kalvinToCelsius(kalvin:Double): Double {
        return kalvin-273.15
    }
}
