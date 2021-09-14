package com.example.weatherapp.data.weather

import com.example.weatherapp.api.Weather
import com.example.weatherapp.api.WeatherResponse
import com.example.weatherapp.ui.weather.WeatherView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import com.example.weatherapp.core.common.Result
import java.lang.Exception
import android.util.Log

class WeatherRepository @Inject constructor(
    private val remoteDataSource: WeatherRemoteDataSource
) {
    companion object {
        private const val TAG = "WeatherRepository"
    }

    suspend fun getWeatherByCityId(
        cityId: Int,
        isFahrenheitMode: Boolean,
    ): Result<WeatherView> = withContext(Dispatchers.IO) {
        when (val response = remoteDataSource.getWeatherByCityId(cityId, isFahrenheitMode)) {
            is Result.Success -> {
                return@withContext Result.Success(serializeWeatherResponse(response.data))
            }
            is Result.Error -> {
                return@withContext Result.Error(response.exception)
            }
        }
    }

    suspend fun getWeatherByCityName(
        cityName: String,
        isFahrenheitMode: Boolean
    ): Result<WeatherView> = withContext(Dispatchers.IO) {
        when (val response = remoteDataSource.getWeatherByCityName(cityName, isFahrenheitMode)) {
            is Result.Success -> {
                return@withContext Result.Success(serializeWeatherResponse(response.data))
            }
            is Result.Error -> {
                return@withContext Result.Error(response.exception)
            }
        }
    }

    suspend fun getWeatherByCoordinates(
        lat: Double,
        lon: Double,
        isFahrenheitMode: Boolean
    ): Result<WeatherView> = withContext(Dispatchers.IO) {
        when (val response = remoteDataSource.getWeatherByCoordinates(lat, lon, isFahrenheitMode)) {
            is Result.Success -> {
                return@withContext Result.Success(serializeWeatherResponse(response.data))
            }
            is Result.Error -> {
                return@withContext Result.Error(response.exception)
            }
        }
    }

    private fun serializeWeatherResponse(
        response: WeatherResponse,
    ): WeatherView {

        return WeatherView(
            cityId = response.id,
            temperatureDegrees = (response.main?.temp ?: 0).toInt(),
            city = response.name ?: "Unknown",
            weatherDescription = response.weather[0].description ?: "Unknown",
            wind = response.wind?.speed.toString(),
            pressure = (response.main?.pressure ?: 0).toInt(),
            humidity = (response.main?.humidity ?: 0).toInt(),
            riskOfRain = response.rain?.h3.toString(),
            weatherType = response.weather[0].id
        )

    }
}