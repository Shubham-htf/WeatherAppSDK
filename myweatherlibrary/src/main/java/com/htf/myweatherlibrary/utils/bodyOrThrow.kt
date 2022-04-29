package com.htf.myweatherlibrary.utils

import org.json.JSONObject
import retrofit2.HttpException
import retrofit2.Response

fun <T> Response<T>.bodyOrThrow(): Any? {
    if (!isSuccessful){
        if (code()==422){
            val error = errorBody()?.string()
            val msg=message()
            println(msg)
            val jsonObject = JSONObject(error!!)
            var message=""
            if (jsonObject.has("errors")){
                val errors = jsonObject.optJSONObject("errors")
                println(errors.length())
                message= if(errors.length()>0){
                    if (errors == null) {
                        errors?.optJSONArray(errors?.names().getString(0))
                            ?.getString(0)!!
                    } else
                        errors.optJSONArray(errors?.names().optString(0))
                            ?.optString(0)!!
                }else{
                    jsonObject.optString("message")
                }
            }
            throw HttpException(this)
        }else{
            throw HttpException(this)
        }
    }
    return (body() )
}

