package com.example.weatherapp.api

import android.content.res.Resources
import android.provider.Settings.System.getString
import org.json.JSONObject
import android.util.Log
import com.example.weatherapp.R

class ApiClient {

    private val url = "http://api.openweathermap.org/data/2.5/weather"
    private val open_weather_maps_app_id = Resources.getSystem().getString(R.string.api_key)

    fun requestByCityName(city: String): JSONObject? {
        Log.d("DEBUG", "City request by name!, city is $city")
        try {
            val response = khttp.get(
                url = this.url,
                params = mapOf(
                    "q" to city,
                    "lang" to "ru",
                    "appid" to open_weather_maps_app_id
                )
            )
            Log.d("DEBUG", response.statusCode.toString())
            Log.d("DEBUG", response.text)
            if (response.statusCode != 200) {
                Log.e("ERROR", response.statusCode.toString())
                Log.e("ERROR", "city:$city")
                return null
            }
            return JSONObject(response.text)

        } catch (exception: Exception) {
            Log.e("ERROR", exception.toString())
            return null
        }
    }

    fun requestByCoordinates(lon: Double, lat: Double): JSONObject? {

        Log.d("DEBUG", "Location request! lon: $lon , lat: $lat")

        try {
            val response = khttp.get(
                url = this.url,
                params = mapOf(
                    "lon" to lon.toString(),
                    "lat" to lat.toString(),
                    "lang" to "ru",
                    "appid" to open_weather_maps_app_id
                )
            )
            Log.d("DEBUG", response.statusCode.toString())
            Log.d("DEBUG", response.text)
            if (response.statusCode != 200) {
                Log.e("ERROR", response.statusCode.toString())
                return null
            }
            return JSONObject(response.text)

        } catch (exception: Exception) {
            Log.e("ERROR", exception.toString())
            Log.e("ERROR", "lon: $lon lat: $lat")
            return null
        }
    }
}
