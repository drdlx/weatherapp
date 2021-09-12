package com.example.weatherapp.ui.weather

import android.Manifest
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.weatherapp.R

class WeatherViewModel : ViewModel() {

    private val _weatherResult = MutableLiveData<WeatherResult>()
    val weatherResult: LiveData<WeatherResult> = _weatherResult

    fun changeLocationCoordinates() {
        /*getCurrentLocation()

        var lat = latitude
        var lon = longitude

        if (latitude == null || longitude == null) {
            try {
                locationManager?.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0L, 0f, locationListener)

            } catch(ex: SecurityException) {
                Log.d("myTag", "Security Exception, no location available")
                Toast.makeText(context, context!!.getString(R.string.coords_issue), Toast.LENGTH_LONG)
                    .show()
            }
        } else {
            updateWeatherDataCoordinates(lat!!, lon!!)
        }*/
    }

    fun changeLocationCity(city: String) {

    }

    private fun getCurrentLocation() {/*

        Permissions.check(this.context, Manifest.permission.ACCESS_FINE_LOCATION, null, object: PermissionHandler() {
            override fun onGranted() {

                locationManager?.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0L, 0f, locationListener)

                val location = locationManager?.getLastKnownLocation(LocationManager.GPS_PROVIDER)

                latitude = location?.latitude
                longitude = location?.longitude
            }
        })*/
    }

    /*private val locationListener: LocationListener = object: LocationListener {

        override fun onLocationChanged(location: Location?) {
            latitude = location?.latitude
            longitude = location?.longitude
            Log.d("DEBUG", "Location changed! Latitude: $latitude longitude: $longitude")
            updateWeatherDataCoordinates(latitude!!, longitude!!)

        }

        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}

        override fun onProviderEnabled(provider: String?) {}

        override fun onProviderDisabled(provider: String?) {}

    }
*/
}
