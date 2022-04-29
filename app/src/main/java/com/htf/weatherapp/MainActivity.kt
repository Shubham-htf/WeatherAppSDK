package com.htf.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.htf.myweatherlibrary.ui.activities.WeatherActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        WeatherActivity.newInstance(currentActivity = this,
            appId = "YOUR API KEY",
            latitude = 28.4089,
            longitude = 77.3178
        )
        finish()
    }
}