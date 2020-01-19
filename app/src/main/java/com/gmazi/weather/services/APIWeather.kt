package com.gmazi.weather.services
import com.gmazi.weather.models.CurrentWeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface APIWeather {
    @GET("weather?q=tbilisi&appid=d37c7266c862c14b319e92c8f9c6e4f3")
    fun getCurrentWeather(): Call<CurrentWeatherResponse>
}