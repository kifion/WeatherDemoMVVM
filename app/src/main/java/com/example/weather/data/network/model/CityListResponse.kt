package com.example.weather.data.network.model

import com.google.gson.annotations.SerializedName

data class CityListResponse(
	@field:SerializedName("startIndex")
	val startIndex: Int,

	@field:SerializedName("cities")
	val cities: List<CityResponse>,

	@field:SerializedName("totalCitiesFound")
	val totalCitiesFound: Int
)

data class CityResponse(
	@field:SerializedName("elevation")
	val elevation: Int,

	@field:SerializedName("feature code")
	val featureCode: String,

	@field:SerializedName("geonameid")
	val geonameid: Int,

	@field:SerializedName("timezone")
	val timezone: String,

	@field:SerializedName("latitude")
	val latitude: Double,

	@field:SerializedName("dem")
	val dem: Int,

	@field:SerializedName("admin2 code")
	val admin2Code: Int,

	@field:SerializedName("population")
	val population: Int,

	@field:SerializedName("alternatenames")
	val alternatenames: String,

	@field:SerializedName("feature class")
	val featureClass: String,

	@field:SerializedName("cc2")
	val cc2: String,

	@field:SerializedName("imageURLs")
	val imageURLs: ImageURLsResponse,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("admin1 code")
	val admin1Code: String,

	@field:SerializedName("country code")
	val countryCode: String,

	@field:SerializedName("admin4 code")
	val admin4Code: String,

	@field:SerializedName("asciiname")
	val asciiname: String,

	@field:SerializedName("modification date")
	val modificationDate: String,

	@field:SerializedName("longitude")
	val longitude: Double
)

data class IOSImageURLsResponse(
	@field:SerializedName("imageURL")
	val imageURL: String
)

data class AndroidImageURLsResponse(
	@field:SerializedName("hdpiImageURL")
	val hdpiImageURL: String,

	@field:SerializedName("xhdpiImageURL")
	val xhdpiImageURL: String,

	@field:SerializedName("mdpiImageURL")
	val mdpiImageURL: String
)

data class ImageURLsResponse(
	@field:SerializedName("androidImageURLs")
	val androidImageURLs: AndroidImageURLsResponse,

	@field:SerializedName("iOSImageURLs")
	val iOSImageURLs: IOSImageURLsResponse
)