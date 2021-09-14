package com.example.weatherapp.ui.weather

data class WeatherView(
    val cityId: Int,
    val temperatureDegrees: Int,
    val city: String,
    val weatherDescription: String,
    val wind: String,
    val pressure: Int,
    val humidity: Int,
    val riskOfRain: String,
    val weatherType: Int,
)
