package com.gmazi.weather.services
import com.gmazi.weather.models.CurrentWeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface APIWeather {
    @GET("api/weather?")
    fun getCurrentWeather(@Query("q") city: String, @Query("appid") appId: String): Call<CurrentWeatherResponse>
}