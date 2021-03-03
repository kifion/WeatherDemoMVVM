package com.example.weather.data.network

import com.example.weather.data.network.model.CityDetailResponse
import com.example.weather.data.network.model.CityListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("/cities")
    suspend fun getCityList(
        @Query("search") search: String?): CityListResponse

    @GET("/cities/{cityID}")
    suspend fun getCityDetails(
        @Path("cityID") cityId: String): CityDetailResponse
}