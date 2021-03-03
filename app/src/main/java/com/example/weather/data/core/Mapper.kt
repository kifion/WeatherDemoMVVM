package com.example.weather.data.core

interface Mapper<E, D> {
    fun fromModel(type: E): D
}