package com.htf.myweatherlibrary.utils

import com.htf.myweatherlibrary.BuildConfig


object Constants {

    val isAppDebug: Boolean = BuildConfig.DEBUG
    object Urls {
        var BASE_URL = "https://api.openweathermap.org/data/2.5/"
    }

}