package com.htf.myweatherlibrary.utils

import com.google.common.truth.Truth
import com.htf.myweatherlibrary.models.WeatherData
import com.htf.myweatherlibrary.netUtils.ApiRepo
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test

class ValidationUtilTest{
    @Test
    fun `empty appId returns false`(){
        // Pass the value to the function of RegistrationUtil class
        // since RegistrationUtil is an object/ singleton we do not need to create its object
        val result = ValidationUtil.validationInput(
            "",
            1.1,
            1.1
        )
        // assertThat() comes from the truth library that we added earlier
        // put result in it and assign the boolean that it should return
        Truth.assertThat(result).isFalse()
    }

    @Test
    fun `check api response return true`(){
        runBlocking {
            val result=
                ApiRepo.getWeatherViaLatLongAsync(28.4089,77.3178,"YOUR OPEN_WEATHER_APP_ID","")
            Truth.assertThat(result is WeatherData).isTrue()
        }
    }
}