package com.htf.myweatherlibrary.netUtils


import com.htf.myweatherlibrary.utils.bodyOrThrow

object ApiRepo {

    private val retrofitClientService =APIClient.apiClient

    suspend fun getWeatherViaLatLongAsync(lat:Double,lon:Double,appId:String,unit:String): Any? {
        return retrofitClientService.getWeatherViaLatLongAsync(
            lat = lat,
            lon = lon,
            appId =appId,
            units = unit
        ).bodyOrThrow()
    }

}