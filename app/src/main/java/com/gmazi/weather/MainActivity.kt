package com.gmazi.weather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.gmazi.weather.models.CurrentWeatherResponse
import com.gmazi.weather.models.WeatherForecast
import com.gmazi.weather.services.APIWeather
import com.gmazi.weather.services.RetrofitEventListener
import com.gmazi.weather.services.WeatherClient
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import java.util.*
import kotlin.math.log
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.math.round


class MainActivity : AppCompatActivity() {

    val apiKey = "d37c7266c862c14b319e92c8f9c6e4f3"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("asd","oncreate")
        CallWeatherData()
        getForecast()
    }

    internal fun CallWeatherData() {
        Log.d("asd","call ")
        WeatherClient.instance.getWeather( object : RetrofitEventListener {
            override  fun onSuccess(call: Call<*>, response: Any) {
                Log.d("asd",response.toString())
                if (response is CurrentWeatherResponse) {
                    Log.d("asd", "-----" + response.main?.temp)
                    Log.d("asd",response.toString())
                    val currentCels = kalvinToCelsius(response.main.temp)

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

    internal fun getForecast(){
        WeatherClient.instance.getForecast(object: RetrofitEventListener{
            override fun onSuccess(call: Call<*>, response: Any) {
                if(response is WeatherForecast){
                    Log.d("asd","got forecast")
                    val cal = Calendar.getInstance()

                    var idx = 0
                    for (list in response.list) {
                        val myDate = getDate(list.dt)
                        cal.setTime(myDate)
                        val day = cal.get(Calendar.DAY_OF_MONTH)
                        Log.d("asd",idx.toString()+" "+ getDates(idx++,response))
                    }
                    date1.text=getDates(0,response)
                    temp11.text=getTemp(0,response)
                    temp12.text=getTemp(7,response)
                    img1.setBackgroundResource(getImage(response,0))
                    img12.setBackgroundResource(getImageNight(response,7))


                    date2.text=getDates(8,response)
                    temp21.text=getTemp(8,response)
                    temp22.text=getTemp(15,response)
                    img2.setBackgroundResource(getImage(response,8))
                    img22.setBackgroundResource(getImageNight(response,15))

                    //16-23
                    date3.text=getDates(16,response)
                    temp31.text=getTemp(16,response)
                    temp32.text=getTemp(23,response)
                    img3.setBackgroundResource(getImage(response,16))
                    img32.setBackgroundResource(getImageNight(response,23))
                    //24-31
                    date4.text=getDates(24,response)
                    temp41.text=getTemp(24,response)
                    temp42.text=getTemp(31,response)
                    img4.setBackgroundResource(getImage(response,24))
                    img42.setBackgroundResource(getImageNight(response,31))
                    //32-39
                    date5.text=getDates(32,response)
                    temp51.text=getTemp(32,response)
                    temp52.text=getTemp(39,response)
                    img5.setBackgroundResource(getImage(response,32))
                    img52.setBackgroundResource(getImageNight(response,39))
                }
            }




            override fun onError(call: Call<*>, t: Throwable) {
                Log.d("asd","error getting forecast")
            }
        })
    }

    fun getImage(response: WeatherForecast,idx:Int): Int {
        return when (response.list[idx].weather[0].main){
            "Clear" -> R.drawable.sun
            "Clouds" -> R.drawable.cloud
            "Rain" -> R.drawable.cloud_rain
            else -> R.drawable.sun
        }
    }

    fun getImageNight(response: WeatherForecast,idx:Int): Int {
        return when (response.list[idx].weather[0].main){
            "Clear" -> R.drawable.moon
            "Clouds" -> R.drawable.moon_cloud
            "Rain" -> R.drawable.moon_cloud_rain
            else -> R.drawable.moon
        }
    }



    fun kalvinToCelsius(kalvin:Double): Double {
        return kalvin-273.15
    }

    fun getDates(idx:Int,response: WeatherForecast): String {
        return formatDate(response.list[idx].dt)
    }

    fun getTemp(idx:Int,response:WeatherForecast): String {
        val temp = kalvinToCelsius(response.list[idx].main.temp)
        return String.format("%.2f",temp)+"°c"
    }

    private fun getDate(epoc: Long): Date? {
        try {
            val netDate = Date(epoc*1000)
            return netDate
        } catch (e: Exception) {
            Log.d("asd","error converting date")
            return null
        }
    }

    fun formatDate(date:Long): String {
        val sdf = SimpleDateFormat("dd-MM-yy")
        val date = Date(date * 1000)
        return sdf.format(date)
    }


}
