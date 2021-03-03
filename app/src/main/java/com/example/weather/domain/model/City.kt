package com.example.weather.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class City(
    var name: String = "",
    var cityId: Int = -1,
    var imageUrl: String = "",
    var timezone: String = "",
    var temperature: String = "",
    val longitude: Double = 0.0,
    var latitude: Double = 0.0
): Parcelable