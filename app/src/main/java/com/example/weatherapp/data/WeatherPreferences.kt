package com.example.weatherapp.data

import android.content.Context

class WeatherPreferences {

    companion object {
        private const val WEATHER_PREFERENCES = "com.example.weatherapp.data.WEATHER_PREFERENCES"
        private const val WEATHER_CITY_ID = "com.example.weatherapp.data.WEATHER_CITY_ID"
        private const val WEATHER_CITY_NAME = "com.example.weatherapp.data.WEATHER_CITY_NAME"
        private const val WEATHER_IS_FAHRENHEIT = "com.example.weatherapp.data.WEATHER_IS_FAHRENHEIT"
        private const val WEATHER_LATITUDE = "com.example.weatherapp.data.WEATHER_LATITUDE"
        private const val WEATHER_LONGITUDE = "com.example.weatherapp.data.WEATHER_LONGITUDE"

        @Volatile private var INSTANCE: WeatherPreferences? = null

        fun getInstance(): WeatherPreferences =
            INSTANCE ?:  synchronized(this) {
                val newInstance = INSTANCE ?: WeatherPreferences()
                    .also { INSTANCE = it }
                newInstance
            }
    }

    fun getCityId(context: Context): Int {
        return context
            .getSharedPreferences(WEATHER_PREFERENCES, 0)
            .getInt(WEATHER_CITY_ID, 2172797)
    }
    fun setCityId(context: Context, cityId: Int) {
        val editor = context.getSharedPreferences(WEATHER_PREFERENCES, 0).edit()
        editor.putInt(WEATHER_CITY_ID, cityId).apply()
    }

    fun getCityName(context: Context): String {
        return context
            .getSharedPreferences(WEATHER_PREFERENCES, 0)
            .getString(WEATHER_CITY_NAME, "New York City").toString()
    }
    fun setCityName(context: Context, city: String) {
        val editor = context.getSharedPreferences(WEATHER_PREFERENCES, 0).edit()
        editor.putString(WEATHER_CITY_NAME, city).apply()
    }

    fun getFahrenheitMode(context: Context): Boolean {
        return context
            .getSharedPreferences(WEATHER_PREFERENCES, 0)
            .getBoolean(WEATHER_IS_FAHRENHEIT, false)
    }

    fun setFahrenheitMode(context: Context, isFahrenheit: Boolean) {
        val editor = context.getSharedPreferences(WEATHER_PREFERENCES, 0).edit()
        editor.putBoolean(WEATHER_IS_FAHRENHEIT, isFahrenheit).apply()
    }

    fun setCoordinates(context: Context, latitude: Long, longitude: Long) {
        val editor = context.getSharedPreferences(WEATHER_PREFERENCES, 0).edit()
        editor.putLong(WEATHER_LATITUDE, latitude)
        editor.putLong(WEATHER_LONGITUDE, longitude).apply()
    }

    fun getLatitude(context: Context): Long {
        return context
            .getSharedPreferences(WEATHER_PREFERENCES, 0)
            .getLong(WEATHER_LATITUDE, -1)
    }

    fun getLongitude(context: Context): Long {
        return context
            .getSharedPreferences(WEATHER_PREFERENCES, 0)
            .getLong(WEATHER_LONGITUDE, -1)
    }

}