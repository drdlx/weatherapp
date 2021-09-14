package com.example.weatherapp.api

import com.example.weatherapp.BuildConfig
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherApi {

    @GET("weather")
    fun getWeatherByCityId(
        @Query("id") cityId: Int,
        @Query("units") units: String,
        @Query("appid") apiKey: String,
    ): Call<WeatherResponse>

    @GET("weather")
    fun getWeatherByCoordinates(
        @Query("lat") lat: Int,
        @Query("lon") lon: Int,
        @Query("units") units: String,
        @Query("appid") apiKey: String,
    ): Call<WeatherResponse>

    @GET("weather")
    fun getWeatherByCityName(
        @Query("q") cityName: String,
        @Query("units") units: String,
        @Query("appid") apiKey: String,
    ): Call<WeatherResponse>
}