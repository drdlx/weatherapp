package com.example.weatherapp.ui.weather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.R
import com.example.weatherapp.core.common.Result
import com.example.weatherapp.data.weather.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
): ViewModel() {

    private val _weatherResult = MutableLiveData<WeatherResult>()
    val weatherResult: LiveData<WeatherResult> = _weatherResult

    private fun updateWeatherUi(result: Result<WeatherView>) {

        when(result) {
            is Result.Success -> {
                _weatherResult.postValue(
                    WeatherResult(
                        success = result.data
                    )
                )
            }
            is Result.Error -> {
                _weatherResult.postValue(
                    WeatherResult(
                        error = R.string.weather_load_error
                    )
                )
            }
        }
    }

    fun updateWeather(
        cityId: Int,
        isFahrenheitMode: Boolean
    ) {

        viewModelScope.launch {
            val result = weatherRepository.getWeatherByCityId(cityId, isFahrenheitMode)
            updateWeatherUi(result)
        }
    }

    fun changeLocationCoordinates(isFahrenheitMode: Boolean) {
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

    fun changeLocationCity(city: String, isFahrenheitMode: Boolean) {

        viewModelScope.launch {
            val result = weatherRepository.getWeatherByCityName(city, isFahrenheitMode)
            updateWeatherUi(result)
        }
    }
}
