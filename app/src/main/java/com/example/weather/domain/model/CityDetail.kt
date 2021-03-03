package com.example.weather.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CityDetail(
	val city: City = City(),
	val weather: List<DayWeather> = arrayListOf()
): Parcelable

@Parcelize
data class DayWeather(
	var weatherType: String = "",
	var high: Int = -1,
	var low: Int = -1,
	var dayOfTheWeek: Int = -1,
	var hourlyWeather: ArrayList<HourlyWeather> = arrayListOf()
): Parcelable

@Parcelize
data class HourlyWeather(
	val weatherType: String = "",
	val hour: Int = -1,
	val temperature: Int = -1,
	val humidity: Double = -1.0,
	val windSpeed: Double = -1.0,
	val rainChance: Double = -1.0
): Parcelable