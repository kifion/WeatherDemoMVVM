package com.example.weather.data.network.mapper

import com.example.weather.Constants
import com.example.weather.data.core.Mapper
import com.example.weather.data.network.model.CityDetailResponse
import com.example.weather.data.network.model.CityResponse
import com.example.weather.data.network.model.DayWeatherResponse
import com.example.weather.data.network.model.HourlyWeatherResponse
import com.example.weather.domain.model.City
import com.example.weather.domain.model.CityDetail
import com.example.weather.domain.model.DayWeather
import com.example.weather.domain.model.HourlyWeather

open class CityDetailsMapper :
    Mapper<CityDetailResponse, CityDetail> {
    override fun fromModel(type: CityDetailResponse): CityDetail {
        var list = ArrayList(type.weather.days.map { DayWeatherMapper().fromModel(it) })
        list.sortBy { it.dayOfTheWeek }
        return CityDetail(
            CityDataMapper().fromModel(type.city),
            list
        )
    }
}

open class CityDataMapper:
    Mapper<CityResponse, City> {
    override fun fromModel(type: CityResponse): City {
        var name = type.asciiname + if(type.admin1Code.isNotEmpty()) ", " + type.admin1Code else ""
        return City(
            name,
            type.geonameid,
            type.imageURLs.androidImageURLs.xhdpiImageURL,
            type.timezone,
            type.elevation.toString() + Constants.DEGREE,
            type.longitude,
            type.latitude
        )
    }
}

open class DayWeatherMapper:
    Mapper<DayWeatherResponse, DayWeather> {
    override fun fromModel(type: DayWeatherResponse): DayWeather {
        return DayWeather(
            type.weatherType,
            type.high,
            type.low,
            type.dayOfTheWeek,
            ArrayList(type.hourlyWeather.map { HourlyWeatherMapper().fromModel(it) })
        )
    }
}

open class HourlyWeatherMapper:
    Mapper<HourlyWeatherResponse, HourlyWeather> {
    override fun fromModel(type: HourlyWeatherResponse): HourlyWeather {
        return HourlyWeather(
            type.weatherType,
            type.hour,
            type.temperature,
            type.humidity,
            type.windSpeed,
            type.rainChance
        )
    }
}