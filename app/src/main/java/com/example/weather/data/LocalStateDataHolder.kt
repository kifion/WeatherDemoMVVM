package com.example.weather.data

import com.example.weather.domain.model.CityDetail
import org.koin.core.component.KoinComponent

class LocalStateDataHolder: KoinComponent {
    var cities: MutableList<CityDetail> = arrayListOf()
    var selectedCity: CityDetail? = null
}