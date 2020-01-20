package com.gmazi.weather.services


import android.util.Log
import com.gmazi.weather.NetworkClient
import com.gmazi.weather.models.CurrentWeatherResponse
import com.gmazi.weather.models.Weather
import java.util.HashMap
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class WeatherClient {
    companion object {
        val instance = WeatherClient()
    }

    private var mApiWeather: APIWeather? = null

    fun getWeather(retrofitEventListener: RetrofitEventListener) {
        val retrofit = NetworkClient.retrofitClient
        mApiWeather = retrofit.create<APIWeather>(APIWeather::class.java)

        val apiWeatherCall = mApiWeather!!.getCurrentWeather()

        apiWeatherCall.enqueue(object : Callback<CurrentWeatherResponse> {
            override fun onResponse(call: Call<CurrentWeatherResponse>?, response: Response<CurrentWeatherResponse>?) {
                Log.d("asd",response.toString())
                if (response?.body() != null) {
                    if (call != null) {
                        retrofitEventListener.onSuccess(call, response?.body()!!)
                    }
                }
            }
            override fun onFailure(call: Call<CurrentWeatherResponse>?, t: Throwable?) {
                if (call != null) {
                    if (t != null) {
                        retrofitEventListener.onError(call, t)
                    }
                }
            }
        })
    }
}