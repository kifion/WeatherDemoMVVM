package com.example.weather.data.repository

import com.example.weather.data.LocalStateDataHolder
import com.example.weather.data.SharedPreferencesUtil
import com.example.weather.domain.model.CityDetail
import com.example.weather.domain.repository.ILocalStateRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class LocalRepository(private val localStateDataHolder: LocalStateDataHolder) :
    ILocalStateRepository, KoinComponent {

    val sharedPreferencesUtil: SharedPreferencesUtil by inject()

    override fun addOrUpdateCity(city: CityDetail) {
        if (!localStateDataHolder.cities.map { it.city.cityId }.contains(city.city.cityId)) {
            localStateDataHolder.cities.add(city)
            sharedPreferencesUtil.setCurrentCity(city.city.cityId)
        } else {
            var index =
                localStateDataHolder.cities.indexOf(localStateDataHolder.cities.first { it.city.cityId == city.city.cityId })
            localStateDataHolder.cities[index] = city
        }
    }

    override fun removeCity(city: CityDetail) {
        localStateDataHolder.cities.remove(city)
    }

    override fun getCities(): MutableList<CityDetail> = localStateDataHolder.cities

    override fun setCurrentCity(city: CityDetail?) {
        if(city != null) {
            localStateDataHolder.selectedCity = city
            sharedPreferencesUtil.setCurrentCity(city.city.cityId)
        } else {
            sharedPreferencesUtil.removeCity()
        }
    }

    override fun getCurrentCity(): CityDetail? = localStateDataHolder.selectedCity
}