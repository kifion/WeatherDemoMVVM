package com.example.weather.domain.repository

import com.example.weather.domain.model.CityDetail

interface ILocalStateRepository {
    fun addOrUpdateCity(city: CityDetail)
    fun removeCity(city: CityDetail)
    fun getCities(): MutableList<CityDetail>

    fun setCurrentCity(city: CityDetail?)
    fun getCurrentCity(): CityDetail?
}