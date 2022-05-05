package com.htf.myweatherlibrary.ui.viewModels

import androidx.lifecycle.viewModelScope
import com.htf.myweatherlibrary.base.BaseViewModel

import com.htf.myweatherlibrary.models.WeatherData
import com.htf.myweatherlibrary.netUtils.ApiRepo
import com.htf.myweatherlibrary.netUtils.Lce
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WeatherViewModel : BaseViewModel() {

    private val _weatherUIState = MutableStateFlow<Lce<WeatherData>?>(null)
    val weatherUIState get() = _weatherUIState.asStateFlow()

    fun getWeatherDetails(
        latitude: Double,
        longitude: Double,
        appId: String,
        unit: String = "Metric" // set the metric as the default value
    ) {
        viewModelScope.launch {
            _weatherUIState.emit(Lce.loading()) // showing progress bar
            try {
                val result = ApiRepo.getWeatherViaLatLongAsync(latitude, longitude, appId, unit) // fetch the data from server in the result
                withContext(Dispatchers.Main) {
                    val weatherData = result as WeatherData //convert result as WeatherData
                    weatherData.current?.unit = unit // add unit in the current day weather to show data in selected unit (C) or (F)
                    weatherData.daily?.map {
                        it.unit = unit  // add unit in the list of day's weather to show data in selected unit (C) or (F)
                    }
                    _weatherUIState.emit(Lce.content(result)) //emit the data for presentation layer
                }
            } catch (e: Exception) {
                _weatherUIState.emit(Lce.error(e.message)) // emit the exception in case of any server error or type conversion error
            }
        }
    }
}