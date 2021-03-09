package com.example.weather.data.repository

import com.example.weather.data.network.ApiService
import com.example.weather.data.network.mapper.CityDetailsMapper
import com.example.weather.data.network.mapper.CityListMapper
import com.example.weather.domain.model.City
import com.example.weather.domain.model.CityDetail
import com.example.weather.domain.repository.INetworkRepository
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@KoinApiExtension
class NetworkRepository : INetworkRepository, KoinComponent {
    private val retrofit: ApiService by inject()
    private val cityListMapper: CityListMapper by inject()
    private val cityDetailsMapper: CityDetailsMapper by inject()

    override suspend fun getCityList(searchString: String): List<City> {
        return cityListMapper.fromModel(retrofit.getCityList(searchString))
    }


    override suspend fun getCityDetail(cityId: Int): CityDetail {
        return cityDetailsMapper.fromModel(retrofit.getCityDetails(cityId.toString()))
    }
}