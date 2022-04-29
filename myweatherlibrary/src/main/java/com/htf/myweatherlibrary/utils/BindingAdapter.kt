package com.htf.myweatherlibrary.utils

import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.text.SimpleDateFormat
import java.util.*

class BindingAdapter {
    companion object {

        @JvmStatic
        @BindingAdapter("day")
        fun TextView.setDay(day: Long) {
            val sdfDate = SimpleDateFormat("dd/MM/yyyy", Locale.US)
            sdfDate.timeZone = TimeZone.getDefault()
            val currentDate = sdfDate.format(Date())
            val sdfDay = SimpleDateFormat("EEEE", Locale.US)
            sdfDay.timeZone = TimeZone.getDefault()

            sdfDay.format(Date(day * 1000))
            text = if (currentDate == sdfDate.format(Date(day * 1000))) "Today" else sdfDay.format(
                Date(day * 1000)
            )

        }

        @JvmStatic
        @BindingAdapter("temp","unit","prefix")
        fun TextView.setTemp(temp:String?,unit:String?,prefix:String="") {
            text = if (temp!=null){
                if (unit=="Metric")
                    "$prefix $temp \u2103"
                else
                    "$prefix $temp \u2109"
            }else
                ""
        }

        @JvmStatic
        @BindingAdapter("speed","unit")
        fun TextView.setWindSpeed(speed:String?,unit:String?){
            text=if (speed!=null){
                if (unit=="Metric")
                    "$speed m/s"
                else
                    "$speed mi/h"
            }else
                ""
        }
    }
}