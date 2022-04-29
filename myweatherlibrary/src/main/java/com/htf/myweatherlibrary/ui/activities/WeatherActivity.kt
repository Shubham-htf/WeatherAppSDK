package com.htf.myweatherlibrary.ui.activities

import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.htf.myweatherlibrary.R
import com.htf.myweatherlibrary.base.BaseActivity
import com.htf.myweatherlibrary.databinding.ActivityWeatherBinding
import com.htf.myweatherlibrary.models.DailyItem
import com.htf.myweatherlibrary.models.WeatherData
import com.htf.myweatherlibrary.netUtils.Lce
import com.htf.myweatherlibrary.ui.adapter.DailyItemAdapter
import com.htf.myweatherlibrary.ui.viewModels.WeatherViewModel
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList


class WeatherActivity : BaseActivity<ActivityWeatherBinding, WeatherViewModel>(WeatherViewModel::class.java) {

    private lateinit var _geocoder: Geocoder
    private lateinit var _addresses: List<Address>
    private var _latitude:Double=0.0
    private var _longitude:Double=0.0 // Faridabad lat long

    private val _adapter by lazy { DailyItemAdapter(arrayListOf()) }
    companion object {
        fun newInstance(currentActivity: AppCompatActivity, appId: String,latitude:Double,longitude:Double) {
            currentActivity.startActivity(
                Intent(
                    currentActivity,
                    WeatherActivity::class.java
                ).apply {
                    putExtra("appId", appId)
                    putExtra("latitude",latitude)
                    putExtra("longitude",longitude)
                })
        }
    }

    override val layout: Int
        get() = R.layout.activity_weather

    private lateinit var _appId:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getExtra()
        setListener()
        initRecycler()
        observeWeatherDataUIState()
    }

    private fun setListener() {
        binding?.switch1?.setOnCheckedChangeListener { compoundButton, b ->
            if (compoundButton.isPressed){
                if (_latitude!=0.0 && _longitude!=0.0)
                    viewModel.getWeatherDetails(_latitude,_longitude,_appId,if (b) "imperial" else "Metric")
            }
        }
    }

    private fun initRecycler() {
        binding?.rvWeatherList?.layoutManager=LinearLayoutManager(this)
        binding?.rvWeatherList?.adapter=_adapter
    }

    private fun getExtra() {
        _appId=intent.getStringExtra("appId") ?: ""
        _latitude=intent.getDoubleExtra("latitude",0.0)
        _longitude=intent.getDoubleExtra("longitude",0.0)
        if (_latitude!=0.0 && _longitude!=0.0){
            viewModel.getWeatherDetails(_latitude,_longitude,_appId)
            getCityNameFromLatLong()
        }

    }

    private fun getCityNameFromLatLong(){
        _geocoder = Geocoder(this, Locale.getDefault())
        _addresses = _geocoder.getFromLocation(
            _latitude,
            _longitude,
            1
        )
        binding?.cityName=_addresses.first().locality ?: ""

    }

    private fun observeWeatherDataUIState(){
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED){
                viewModel.weatherUIState.collect{
                    it?.let {
                        when(it){
                            is Lce.Loading->{
                                binding?.progressBar?.visibility=View.VISIBLE
                            }

                            is Lce.Content->{
                                binding?.progressBar?.visibility=View.GONE
                                it.content.setWeatherData()
                            }

                            is Lce.Error->{
                                Toast.makeText(this@WeatherActivity,it.error,Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun WeatherData.setWeatherData(){
        binding?.currentData=current
        daily?.let { list->
            _adapter.setNewList(list)
        }

    }




}