package com.example.weatherapp.ui.weather

import android.Manifest
import android.app.Activity
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.pm.PackageManager
import android.location.Criteria
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.support.v4.content.ContextCompat
import android.support.v4.content.ContextCompat.getSystemService
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.weatherapp.CityStoring
import com.example.weatherapp.R
import com.example.weatherapp.api.ApiClient
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.nabinbhandari.android.permissions.PermissionHandler
import com.nabinbhandari.android.permissions.Permissions
import kotlinx.android.synthetic.main.weather_fragment.*
import org.json.JSONObject

class WeatherFragment : Fragment() {

    companion object {
        fun newInstance() = WeatherFragment()
    }

    private val handler: Handler = Handler()

    private var locationManager: LocationManager? = null

    private var lattitude: Double? = null
    private var longitude: Double? = null

    private lateinit var viewModel: WeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val rootView = inflater.inflate(R.layout.weather_fragment, container, false)

        val changeCity: TextView? = rootView.findViewById(R.id.changeCity) as? TextView
        changeCity!!.setOnClickListener {
            Log.d("DEBUG", "OnClick by CityName")
            showInputCityVisilibity()
        }

        val degreesTypeToggle: Switch? = rootView.findViewById(R.id.degreesTypeToggle) as? Switch
        degreesTypeToggle!!.setOnClickListener {
            Log.d("DEBUG", "Fahrenheit mode!")
            degreesTypeToggle.isChecked = !degreesTypeToggle.isChecked
            CityStoring(this.activity).setFahrenheitMode(degreesTypeToggle.isChecked)
            //loadData()

        }

        val changeCityOk = rootView.findViewById(R.id.changeCityOk) as? Button
        changeCityOk!!.setOnClickListener {
            Log.d("DEBUG", "Ok button pressed!")
            val cityLocation = changeCityInput.text
            changeCity(cityLocation.toString())
            hideInputCityVisilibity()
        }

        val myLocation = rootView.findViewById(R.id.myLocation) as? TextView
        myLocation!!.setOnClickListener {
            Log.d("DEBUG", "My Location!")
            changeLocation()
        }

        return rootView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val city = CityStoring(this.activity).getCity()

        locationManager =
            this.activity!!.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        Log.d("DEBUG", "The city is $city")
        if (city == "") {
            changeLocation()
        } else {
            Log.d("DEBUG, ","Load data!")
            updateWeatherData(city)
        }

    }

    private fun loadData() {
        val city = CityStoring(this.activity).getCity()
        Log.d("DEBUG", "The city is $city")
        if (city == "") {
            changeLocation()
        } else {
            Log.d("DEBUG, ","Load data!")
            updateWeatherData(city)
        }
    }

    private fun updateWeatherData(city: String) {
        Log.d("DEBUG", "Update weather data!")
        Thread {
            Log.d("DEBUG", "Thread update via string start!")

            Log.d("DEBUG", "RUN!")
            val jsonObject = ApiClient().requestByCityName(city)
            if(jsonObject == null) {
                handler.post {
                    Log.d("DEBUG", "Not found")
                    Toast.makeText(context, context!!.getString(R.string.place_not_found), Toast.LENGTH_LONG).show()
                }
            } else {
                handler.post {
                    Log.d("DEBUG", "Render")
                    renderWeather(jsonObject)
                }
            }

        }.start()

    }

    private fun updateWeatherDataCoordinates(lat: Double, lon: Double) {
        Log.d("DEBUG", "Update weather data coordinates!")
        Thread {
            Log.d("DEBUG", "Thread update coordinates start!")

            Log.d("DEBUG", "RUN!")
            val jsonObject = ApiClient().requestByCoordinates(lon, lat)
            if(jsonObject == null) {
                handler.post {
                    Log.d("DEBUG", "Not found")
                    Toast.makeText(context, context!!.getString(R.string.place_not_found), Toast.LENGTH_LONG).show()
                }
            } else {
                handler.post {
                    Log.d("DEBUG", "Render")
                    renderWeather(jsonObject)
                }
            }

        }.start()

    }

    private fun renderWeather(jsonObject: JSONObject) {
        try {
            val city = jsonObject.getString("name") + jsonObject.getJSONObject("sys").getString("country")
            cityName.text = city
            
            val main = jsonObject.getJSONObject("main")

            if(CityStoring(this.activity).getFahrenheitMode()) {
                degreesTypeToggle.isChecked = true
            }

            val degreesKelvin = main.getString("temp").toFloat()
            val degrees = if ((degreesTypeToggle.isChecked)) {
                (((degreesKelvin - 273.15) * (9/5) + 32).toInt()).toString()
            } else {
                ((degreesKelvin - 273.15).toInt()).toString()
            }

            val weatherDegreesText = degrees + getString(R.string.degree_symbol)
            weatherDegrees.text = weatherDegreesText

            val weather = jsonObject.getJSONArray("weather").getJSONObject(0)
            weatherDescription.text = weather.getString("main")

            wind.text = jsonObject.getJSONObject("wind").getString("speed")

            pressure.text = main.getString("pressure")

            moisture.text = main.getString("humidity")

            val rainPossibilityText = main.getString("humidity") + "%"
            rainPossibility.text = rainPossibilityText

            setWeatherIcon(weather.getInt("id"))

            CityStoring(this.activity).setCity(city)

        } catch (e: Exception) {
            Log.e("SimpleWeather", e.toString())
        }
    }

    private fun showInputCityVisilibity() {
        textInputLayout?.visibility = View.VISIBLE
    }
    private fun hideInputCityVisilibity() {
        textInputLayout?.visibility = View.GONE
    }

    private fun setWeatherIcon(weatherId: Int) {
        val id = weatherId / 100
        if (weatherId == 800) {
            weatherIcon.setImageResource(R.drawable.sun)
        } else {
            when(id) {
                2 ->  weatherIcon.setImageResource(R.drawable.strom)
                5 -> weatherIcon.setImageResource(R.drawable.rain)
                7 -> weatherIcon.setImageResource(R.drawable.cloud)
                else -> weatherIcon.setImageResource(R.drawable.partly_cloudy)
            }
        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this.activity!!).get(WeatherViewModel::class.java)
        // TODO: Use the ViewModel
    }

    private fun changeCity(cityName: String) {
        Log.d("DEBUG","change city name!")
        updateWeatherData(cityName)
        CityStoring(this.activity).setCity(cityName)
    }

    private fun getCurrentLocation() {

        Permissions.check(this.context, Manifest.permission.ACCESS_FINE_LOCATION, null, object: PermissionHandler() {
            override fun onGranted() {

                val location = locationManager?.getLastKnownLocation(LocationManager.GPS_PROVIDER)

                lattitude = location?.latitude
                longitude = location?.longitude
            }
        })
    }

    private fun changeLocation() {
        getCurrentLocation()

        val lat = lattitude
        val lon = longitude
        if (lattitude == null || longitude == null) {
            Toast.makeText(context, context!!.getString(R.string.coords_issue), Toast.LENGTH_LONG)
                .show()
        } else {
            updateWeatherDataCoordinates(lat!!, lon!!)
        }
    }

}
