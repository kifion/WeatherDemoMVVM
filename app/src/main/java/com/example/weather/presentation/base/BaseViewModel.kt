package com.example.weather.presentation.base

import androidx.annotation.CallSuper
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

abstract class BaseViewModel: ViewModel() {
    private val job = Job()
    val uiScope = CoroutineScope(Dispatchers.Main + job)

    val isDataLoading = MutableLiveData<Boolean>()
    val exception = MutableLiveData<Throwable>()

    @CallSuper
    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

    open fun setLoading(isLoading: Boolean? = true) {
        isDataLoading.value = isLoading

        if (isLoading == true) {
            exception.value = null
        }
    }
}