package com.htf.myweatherlibrary.base

import java.io.IOException

sealed class Output<out T : Any> {
    data class Success<out T : Any>(val output: T) : Output<T>()
    data class Error(val exception: IOException) : Output<Nothing>()
}

