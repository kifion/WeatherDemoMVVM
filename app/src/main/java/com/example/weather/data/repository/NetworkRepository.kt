package com.example.weather.data.repository

import com.example.weather.data.network.ApiService
import com.example.weather.data.network.mapper.CityDetailsMapper
import com.example.weather.data.network.mapper.CityListMapper
import com.example.weather.domain.model.City
import com.example.weather.domain.model.CityDetail
import com.example.weather.domain.model.Error
import com.example.weather.domain.model.Event
import com.example.weather.domain.repository.INetworkRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class NetworkRepository : INetworkRepository, KoinComponent {
    private val retrofit: ApiService by inject()
    private val cityListMapper: CityListMapper by inject()
    private val cityDetailsMapper: CityDetailsMapper by inject()

    override suspend fun getCityList(searchString: String): Event<List<City>> {
        return try {
            Event.success(cityListMapper.fromModel(retrofit.getCityList(searchString)))
        } catch (exception: Exception) {
            Event.error(Error.CONNECTION_PROBLEM)
        }
    }


    override suspend fun getCityDetail(cityId: Int): Event<CityDetail> {
        return try {
            Event.success(cityDetailsMapper.fromModel(retrofit.getCityDetails(cityId.toString())))
        } catch (exception: Exception) {
            Event.error(Error.CONNECTION_PROBLEM)
        }
    }

}