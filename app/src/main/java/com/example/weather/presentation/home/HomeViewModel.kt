package com.example.weather.presentation.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.weather.data.SharedPreferencesUtil
import com.example.weather.domain.model.CityDetail
import com.example.weather.domain.model.Error
import com.example.weather.domain.model.Event
import com.example.weather.domain.repository.ILocalStateRepository
import com.example.weather.domain.repository.INetworkRepository
import com.example.weather.presentation.base.BaseViewModel
import kotlinx.coroutines.launch
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@KoinApiExtension
class HomeViewModel : BaseViewModel(), KoinComponent {
    private val networkRepository: INetworkRepository by inject()
    private val localStateRepository: ILocalStateRepository by inject()
    private val sharedPreferencesUtil: SharedPreferencesUtil by inject()

    val cityDetail = MutableLiveData<Event<CityDetail>>()

    val update = MutableLiveData<Boolean>()
    val actionSearchButtonClicked = MutableLiveData<Boolean>()

    fun getCityDetailsByCity(cityId: Int) {
        this.viewModelScope.launch {
            try {
                val response = networkRepository.getCityDetail(cityId)
                setSelectCity(response)
                cityDetail.postValue(Event.success(response))
            } catch (exception: Exception) {
                cityDetail.postValue(Event.error(Error.CONNECTION_PROBLEM))
            }
        }
    }

    fun onSearchButtonClicked() {
        this.actionSearchButtonClicked.value = true
    }

    fun onRemoveButtonClicked() {
        localStateRepository.getCurrentCity()?.let {
            localStateRepository.removeCity(it)

            if(localStateRepository.getCities().size > 0) {
                localStateRepository.setCurrentCity(localStateRepository.getCities().last())
            } else {
                localStateRepository.setCurrentCity(null)
            }
            this.update.value = true
        }
    }

    fun getCurrentCity() = localStateRepository.getCurrentCity()

    fun getCityList(): MutableList<CityDetail> = localStateRepository.getCities()

    fun setSelectCity(cityDetail: CityDetail) {
        localStateRepository.setCurrentCity(cityDetail)
        localStateRepository.addOrUpdateCity(cityDetail)
    }

    fun initData() {
        if(sharedPreferencesUtil.getCurrentCity() != 0) {
            setLoading(true)
            getCityDetailsByCity(sharedPreferencesUtil.getCurrentCity())
        }
    }
}