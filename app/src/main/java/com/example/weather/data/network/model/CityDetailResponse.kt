package com.example.weather.data.network.model

import com.google.gson.annotations.SerializedName

data class CityDetailResponse(
	@field:SerializedName("city")
	val city: CityResponse,

	@field:SerializedName("weather")
	val weather: WeatherResponse
)

data class WeatherResponse(
	@field:SerializedName("days")
	val days: List<DayWeatherResponse>,

	@field:SerializedName("id")
	val id: Int
)

data class HourlyWeatherResponse(
	@field:SerializedName("weatherType")
	val weatherType: String,

	@field:SerializedName("hour")
	val hour: Int,

	@field:SerializedName("temperature")
	val temperature: Int,

	@field:SerializedName("humidity")
	val humidity: Double,

	@field:SerializedName("windSpeed")
	val windSpeed: Double,

	@field:SerializedName("rainChance")
	val rainChance: Double
)

data class DayWeatherResponse(
	@field:SerializedName("weatherType")
	val weatherType: String,

	@field:SerializedName("high")
	val high: Int,

	@field:SerializedName("low")
	val low: Int,

	@field:SerializedName("dayOfTheWeek")
	val dayOfTheWeek: Int,

	@field:SerializedName("hourlyWeather")
	val hourlyWeather: List<HourlyWeatherResponse>
)
