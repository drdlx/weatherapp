package com.example.weatherapp.ui.weather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(): ViewModel() {

    private val _weatherResult = MutableLiveData<WeatherResult>()
    val weatherResult: LiveData<WeatherResult> = _weatherResult

    fun updateWeather() {
        _weatherResult.postValue(
            WeatherResult(
            success = WeatherView(
                temperatureDegrees = 25,
                city = "New York",
                weatherDescription = "Good",
                wind = "NW",
                pressure = 25,
                humidity = 36,
                riskOfRain = "Maybe",
                weatherType = 200
            )
        ))
    }

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
