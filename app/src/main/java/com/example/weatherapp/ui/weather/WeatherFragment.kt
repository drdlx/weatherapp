package com.example.weatherapp.ui.weather

import androidx.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.location.LocationManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.weatherapp.data.CityStoring
import com.example.weatherapp.R
import com.example.weatherapp.api.ApiClient
import kotlinx.android.synthetic.main.weather_fragment.*
import org.json.JSONObject

class WeatherFragment : Fragment() {

    companion object {
        private const val TAG = "WeatherFragment"
        fun newInstance() = WeatherFragment()
    }

   /* private val handler: Handler = Handler()

    private var locationManager: LocationManager? = null

    private var latitude: Double? = null
    private var longitude: Double? = null
*/
    //private var weatherViewModel: WeatherViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        /*
        val city = CityStoring(this.activity).getCity()
        Log.d("DEBUG", "The city is $city")

        if (city == "") {
            Log.d("DEBUG", "Empty string! Let's get the location")
            //changeLocation()
        } else {
            Log.d("DEBUG, ","Found saved city! Load data")
            changeCity(city)
        }*/

        val rootView = inflater.inflate(R.layout.weather_fragment, container, false)

        /*val changeCity: TextView? = rootView.findViewById(R.id.changeCity) as? TextView
        changeCity!!.setOnClickListener {
            Log.d("DEBUG", "OnClick by CityName")
            showInputCityVisilibity()
        }*/

//        val degreesTypeToggle = requireView().findViewById<ToggleButton>(R.id.degreesTypeToggle)

        /*degreesTypeToggle.setOnCheckedChangeListener { _: CompoundButton, _: Boolean ->
            Log.d(TAG, "Change temperature display mode!")
            Log.d("SWITCH", degreesTypeToggle.isChecked.toString())

            CityStoring(this.activity).setFahrenheitMode(degreesTypeToggle.isChecked)
            Log.d("SWITCH", CityStoring(this.activity).getFahrenheitMode().toString())

            updateWeatherData(CityStoring(this.activity).getCity())

        }
        */

        /*val changeCityOk = requireActivity().findViewById<Button>(R.id.changeCityOk)

        changeCityOk.setOnClickListener {
            Log.d("DEBUG", "Ok button pressed!")
            val cityLocation = changeCityInput.text
            weatherViewModel.changeLocationCity(cityLocation.toString())
            hideInputCityVisilibity()
        }*/

        /*val myLocation = requireActivity().findViewById<TextView>(R.id.myLocation)
        myLocation.setOnClickListener {
            Log.d("DEBUG", "My Location!")
            weatherViewModel.changeLocationCoordinates()
        }*/

        return rootView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //weatherViewModel.changeLocationCoordinates()
    }

    /*private fun updateWeatherData() {
        Log.d("DEBUG", "Update weather data!")
        Thread {
            Log.d("DEBUG", "Thread update via string start!")

            Log.d("DEBUG", "RUN!")
            val jsonObject = ApiClient().requestByCityName(city)

            if(jsonObject == null) {
                handler.post {
                    Log.d("DEBUG", "Not found")
                    Toast.makeText(context, resources.getString(R.string.place_not_found), Toast.LENGTH_LONG).show()
                }
            } else {
                handler.post {
                    Log.d("DEBUG", "Render")
                    renderWeather(jsonObject)
                }
            }

        }.start()

    }*/

    /*private fun updateUiWithWeather(model: WeatherView) {
        weatherDegrees.text = model.temperatureDegrees
    }*/

    /*private fun updateWeatherDataCoordinates(lat: Double, lon: Double) {
        Log.d("DEBUG", "Update weather data coordinates!")
        Thread {
            Log.d("DEBUG", "Thread update coordinates start!")

            Log.d("DEBUG", "RUN!")
            val jsonObject = ApiClient().requestByCoordinates(lon, lat)
            if(jsonObject == null) {
                handler.post {
                    Log.d("DEBUG", "Not found")
                    Toast.makeText(context, resources.getString(R.string.place_not_found), Toast.LENGTH_LONG).show()
                }
            } else {
                handler.post {
                    Log.d("DEBUG", "Render")
                    renderWeather(jsonObject)
                }
            }

        }.start()

    }
*/
    /*private fun renderWeather(jsonObject: JSONObject) {
        try {
            val city = jsonObject.getString("name")
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

            val windText = jsonObject.getJSONObject("wind").getString("speed") + " " + context!!.getString(R.string.wind_speed)
            wind.text = windText

            val pressureText = main.getString("pressure") + " " + context!!.getString(R.string.pressure_measure)
            pressure.text = pressureText

            val moistureText = main.getString("humidity") + "%"
            moisture.text = moistureText

            val rainPossibilityText = main.getString("humidity") + "%"
            rainPossibility.text = rainPossibilityText

            setWeatherIcon(weather.getInt("id"))

            CityStoring(this.activity).setCity(city)

        } catch (e: Exception) {
            Log.e("SimpleWeather", e.toString())
        }
    }*/

    private fun showInputCityVisilibity() {
        textInputLayout?.visibility = View.VISIBLE
    }
    private fun hideInputCityVisilibity() {
        textInputLayout?.visibility = View.GONE
    }

    /*private fun setWeatherIcon(weatherId: Int) {
        when(weatherId / 100) {
            2 ->  weatherIcon.setImageResource(R.drawable.strom)
            5 -> weatherIcon.setImageResource(R.drawable.rain)
            7 -> weatherIcon.setImageResource(R.drawable.cloud)
            8 -> weatherIcon.setImageResource(R.drawable.sun)
            else -> weatherIcon.setImageResource(R.drawable.partly_cloudy)
        }

    }*/

    /*private fun changeCity(cityName: String) {
        Log.d("DEBUG","change city name!")
        weatherViewModel.updateWeatherData(cityName)
        CityStoring(this.activity).setCity(cityName)
    }*/
}
