package com.example.weatherapp.ui.weather

data class WeatherResult(
    val success: WeatherView? = null,
    val error: Int? = null
)
