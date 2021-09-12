package com.example.weatherapp.data

import android.app.Activity
import android.content.SharedPreferences

class CityStoring(activity: Activity?) {

    val prefs: SharedPreferences = activity!!.getPreferences(Activity.MODE_PRIVATE)

    private val city = prefs.getString("city", "")

    private val fahrenheitMode = prefs.getString("fahrenheit", "false")

    /*private val latitude = prefs.getString("latitude", "")
    private val longitude = prefs.getString("longitude", "")*/

    fun getCity(): String {
        return ""
    }

    fun setCity(city: String) {
        prefs.edit().putString("city", city).apply()
    }

    fun getFahrenheitMode(): Boolean {
        return this.fahrenheitMode.toBoolean()
    }

    fun setFahrenheitMode(isFahrenheit: Boolean) {
        prefs.edit().putBoolean("fahrenheit", isFahrenheit).apply()
    }

    /*fun setCoordinates(latitude: Long, longitude: Long) {
        prefs.edit().putString("latitude", latitude.toString()).apply()
        prefs.edit().putString("longitude", longitude.toString()).apply()
    }

    fun getLatitude(): Long {
        return latitude.toLong()
    }

    fun getLongitude(): Long {
        return longitude.toLong()
    }*/

}