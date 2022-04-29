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

    private val _weatherUIState= MutableStateFlow<Lce<WeatherData>?>(null)
    val weatherUIState get() = _weatherUIState.asStateFlow()

    fun getWeatherDetails(latitude:Double,longitude:Double,appId:String,unit:String="Metric"){
        viewModelScope.launch {
            _weatherUIState.emit(Lce.loading())
            try {
                val res= ApiRepo.getWeatherViaLatLongAsync(latitude,longitude,appId,unit)
                withContext(Dispatchers.Main){
                    val weatherData=res as WeatherData
                    weatherData.current?.unit=unit
                    weatherData.daily?.map {
                        it.unit=unit
                    }
                    _weatherUIState.emit(Lce.content(res))
                }
            }catch (e:Exception){
                _weatherUIState.emit(Lce.error(e.message))
            }
        }
    }
}