package com.example.weather.presentation.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.weather.domain.model.City
import com.example.weather.domain.model.Error
import com.example.weather.domain.model.Event
import com.example.weather.domain.repository.INetworkRepository
import com.example.weather.presentation.base.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.component.inject
import java.lang.Exception

@KoinApiExtension
class SearchViewModel : BaseViewModel(), KoinComponent {
    private val repository: INetworkRepository by inject()

    var cities = MutableLiveData<Event<List<City>>>()

    fun getCities(search: String) {
        this.onCleared()
        cities.postValue(Event.loading())
        this.viewModelScope.launch {
            delay(200)
            try {
                cities.postValue(Event.success(repository.getCityList(search)))
            } catch (exception: Exception) {
                cities.postValue(Event.error(Error.CONNECTION_PROBLEM))
            }
        }
    }
}