package com.htf.myweatherlibrary.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel : ViewModel() {

    //create a new Job
    private val parentJob = SupervisorJob()

    //create a co-routine context with the job and the dispatcher
    private val coroutineContext: CoroutineContext get() = parentJob + Dispatchers.IO

    //create a co-routine scope with the co-routine context
    val scope = CoroutineScope(coroutineContext)


    private fun cancelRequests() = coroutineContext.cancel()
    override fun onCleared() {
        super.onCleared()
        cancelRequests()
    }
}