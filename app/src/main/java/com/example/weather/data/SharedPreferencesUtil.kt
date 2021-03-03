package com.example.weather.data

import android.content.Context

class SharedPreferencesUtil(private val context: Context? = null) {

    companion object {
        private const val PREFS_NAME = "SharedPreferences"
        const val CITY_ID = "cityId"
    }

    fun getCurrentCity(): Int {
        val settings = context?.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return settings?.getInt(CITY_ID, 0) ?: 0
    }

    fun setCurrentCity(propertyState: Int) {
        val settings = context?.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        settings?.edit()?.putInt(CITY_ID, propertyState)?.apply()
    }

    fun removeCity() {
        val settings = context?.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        settings?.edit()?.remove(CITY_ID)?.apply()
    }
}