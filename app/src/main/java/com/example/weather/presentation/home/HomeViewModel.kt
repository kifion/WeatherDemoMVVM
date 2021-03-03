package com.example.weather.presentation.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.weather.data.SharedPreferencesUtil
import com.example.weather.domain.model.City
import com.example.weather.domain.model.CityDetail
import com.example.weather.domain.model.Error
import com.example.weather.domain.repository.ILocalStateRepository
import com.example.weather.domain.repository.INetworkRepository
import com.example.weather.presentation.base.BaseViewModel
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class HomeViewModel : BaseViewModel(), KoinComponent {
    private val networkRepository: INetworkRepository by inject()
    private val localStateRepository: ILocalStateRepository by inject()
    private val sharedPreferencesUtil: SharedPreferencesUtil by inject()

    val update = MutableLiveData<Boolean>()
    val actionSearchButtonClicked = MutableLiveData<Boolean>()
    val getData = MutableLiveData<Error>()

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

    fun getCityDetailsByCity(cityId: Int) {
        this.viewModelScope.launch {
            var cityWeather = networkRepository.getCityDetail(cityId)
            if(cityWeather.error == null) {
                cityWeather.data?.let {
                    setSelectCity(it)
                }
                getData.postValue(null)
            } else {
                getData.postValue(cityWeather.error)
            }

        }
    }

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