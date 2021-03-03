package com.example.weather.presentation.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.weather.domain.model.City
import com.example.weather.domain.model.Event
import com.example.weather.domain.repository.INetworkRepository
import com.example.weather.presentation.base.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class SearchViewModel : BaseViewModel(), KoinComponent {
    var cities = MutableLiveData<Event<List<City>>>()
    var job: Job? = null

    var repository: INetworkRepository = get()

    fun getCities(search: String) {
        setLoading(false)
        job?.cancel()

        setLoading()
        job = this.viewModelScope.launch {
            delay(200)
            cities.postValue(repository.getCityList(search))
        }
    }
}