package com.htf.myweatherlibrary.utils

object ValidationUtil {
    fun validationInput(
        appId : String,
        latitude:Double,
        longitude:Double
    ) : Boolean {
        if (appId.isEmpty() || latitude==0.0 || longitude==0.0){
            return false
        }
        return true
    }

}