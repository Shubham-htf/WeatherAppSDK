package com.htf.myweatherlibrary.netUtils

import com.htf.myweatherlibrary.models.WeatherData
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @GET("onecall")
    suspend fun getWeatherViaLatLongAsync(
        @Query("lat") lat :Double,
        @Query("lon") lon :Double,
        @Query("exclude") exclude :String="hourly,minutely",
        @Query("units") units :String,
        @Query("cnt") cnt :String="4",
        @Query("appid") appId:String
    ):Response<WeatherData>

}