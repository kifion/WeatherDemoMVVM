package com.example.weather.domain.repository

import com.example.weather.domain.model.City
import com.example.weather.domain.model.CityDetail
import com.example.weather.domain.model.Event

interface INetworkRepository {
    suspend fun getCityList(searchString: String): Event<List<City>>
    suspend fun getCityDetail(cityId: Int): Event<CityDetail>
}