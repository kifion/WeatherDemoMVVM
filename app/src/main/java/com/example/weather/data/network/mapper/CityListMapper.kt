package com.example.weather.data.network.mapper

import com.example.weather.data.core.Mapper
import com.example.weather.data.network.model.CityListResponse
import com.example.weather.data.network.model.CityResponse
import com.example.weather.domain.model.City


open class CityListMapper:
    Mapper<CityListResponse, List<City>> {
    override fun fromModel(type: CityListResponse): List<City> {
        return type.cities.map { CityMapper().fromModel(it) }
    }
}

open class CityMapper: Mapper<CityResponse, City> {
    override fun fromModel(type: CityResponse): City {
        return City(
            type.name,
            type.geonameid
        )
    }
}