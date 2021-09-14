package com.example.weatherapp.ui.weather

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.StringRes
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.weatherapp.R
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.weather_fragment.*
import kotlinx.android.synthetic.main.weather_fragment.view.*
import android.util.Log
import com.example.weatherapp.data.WeatherPreferences

@AndroidEntryPoint
class WeatherFragment : Fragment() {

    companion object {
        private const val TAG = "WeatherFragment"
        fun newInstance() = WeatherFragment()
    }

    private val weatherViewModel: WeatherViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return inflater.inflate(R.layout.weather_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loading.visibility = View.VISIBLE

        val cityId = WeatherPreferences.getInstance().getCityId(requireContext())
        weatherViewModel.updateWeather(cityId)

        degreesTypeToggle.setOnCheckedChangeListener { _: CompoundButton, _: Boolean ->
            Log.d(TAG, "Change temperature display mode!")
            Log.d(TAG, degreesTypeToggle.isChecked.toString())

            /*CityStoring(this.activity).setFahrenheitMode(degreesTypeToggle.isChecked)
            Log.d("SWITCH", CityStoring(this.activity).getFahrenheitMode().toString())

            updateWeatherData(CityStoring(this.activity).getCity())*/

        }

        changeCity.setOnClickListener {
            Log.d("DEBUG", "Display change city form")
            textInputLayout.visibility = View.VISIBLE
        }

        changeCityOk.setOnClickListener {
            Log.d("DEBUG", "Ok button pressed!")
            val cityLocation = changeCityInput.text
            loading.visibility = View.VISIBLE
            weatherViewModel.changeLocationCity(cityLocation.toString())
            textInputLayout.visibility = View.GONE
        }

        weatherViewModel.weatherResult.observe(viewLifecycleOwner, Observer {
            val weatherResult = it ?: return@Observer

            if (weatherResult.error != null) {
                showWeatherLoadFailed(weatherResult.error)
                requireActivity().setResult(Activity.RESULT_CANCELED)
            }

            if (weatherResult.success != null) {

                val id = weatherResult.success.cityId

                WeatherPreferences.getInstance()
                    .setCityId(requireContext(), id)

                updateUiWithWeather(
                    weatherResult.success
                )
                requireActivity().setResult(Activity.RESULT_OK)
            }
        })


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        weatherViewModel.changeLocationCoordinates()
    }

    private fun updateUiWithWeather(weatherView: WeatherView) {
        cityName.text = weatherView.city
        weatherDescription.text = weatherView.weatherDescription
        weatherDegrees.text = getString(R.string.degrees, weatherView.temperatureDegrees)
        wind.text = weatherView.wind
        pressure.text = getString(R.string.pressure, weatherView.pressure)
        humidity.text = getString(R.string.humidity, weatherView.humidity)
        rainPossibility.text = getString(R.string.rain_possibility, weatherView.humidity)
        updateWeatherIcon(weatherView.weatherType)
        loading.visibility = View.GONE
    }

    private fun showWeatherLoadFailed(@StringRes errorString: Int) {
        Toast.makeText(context, errorString, Toast.LENGTH_SHORT).show()
        /*Snackbar.make(
            requireView().findViewById(R.id.content),
            errorString,
            Snackbar.LENGTH_SHORT).show()*/
    }

    private fun updateWeatherIcon(weatherType: Int) {
        when(weatherType / 100) {
            2 ->  weatherIcon.setImageResource(R.drawable.strom)
            5 -> weatherIcon.setImageResource(R.drawable.rain)
            7 -> weatherIcon.setImageResource(R.drawable.cloud)
            8 -> weatherIcon.setImageResource(R.drawable.sun)
            else -> weatherIcon.setImageResource(R.drawable.partly_cloudy)
        }
    }

    /*private fun changeCity(cityName: String) {
        Log.d("DEBUG","change city name!")
        weatherViewModel.updateWeatherData(cityName)
        CityStoring(this.activity).setCity(cityName)
    }*/
}
