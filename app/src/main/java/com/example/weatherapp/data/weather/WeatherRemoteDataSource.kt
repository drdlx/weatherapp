package com.example.weatherapp.data.weather

import android.util.Log
import com.example.weatherapp.BuildConfig
import com.example.weatherapp.api.WeatherNetwork
import com.example.weatherapp.api.WeatherResponse
import com.example.weatherapp.core.common.Result
import com.example.weatherapp.ui.weather.WeatherView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject
import javax.security.auth.callback.Callback

class WeatherRemoteDataSource @Inject constructor() {

    fun getWeatherByCityId(
        cityId: Int,
        isFahrenheitMode: Boolean,
    ): Result<WeatherResponse> {
        val units = if (isFahrenheitMode) {
            "imperial"
        } else {
            "metric"
        }
        val response = WeatherNetwork
            .retrofit
            .getWeatherByCityId(
                cityId,
                units,
                BuildConfig.WEATHER_API_KEY
            ).execute()
        Log.d("DEBUG", "${response}")
        if (response.isSuccessful) {
            val result = response.body()
            if (result != null) {
                return Result.Success(result)
            } else {
                return Result.Error(Exception("Response is empty!"))
            }
        } else {
            return Result.Error(Exception("${response.errorBody()}"))
        }
    }

    fun getWeatherByCityName(
        cityName: String,
        isFahrenheitMode: Boolean,
    ): Result<WeatherResponse> {
        val units = if (isFahrenheitMode) {
            "imperial"
        } else {
            "metric"
        }
        val response = WeatherNetwork
            .retrofit
            .getWeatherByCityName(
                cityName,
                units,
                BuildConfig.WEATHER_API_KEY
            ).execute()

        Log.d("DEBUG", "$response")
        return if (response.isSuccessful) {
            val result = response.body()
            if (result != null) {
                Result.Success(result)
            } else {
                Result.Error(Exception("Response is empty!"))
            }
        } else {
            Result.Error(Exception("${response.errorBody()}"))
        }
    }

    companion object {
        private const val TAG = "WeatherRemoteDataSource"
    }


}