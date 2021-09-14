package com.example.weatherapp.ui.weather

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.PackageManager
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
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.weatherapp.data.WeatherPreferences
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

@AndroidEntryPoint
class WeatherFragment : Fragment() {

    companion object {
        private const val TAG = "WeatherFragment"
        fun newInstance() = WeatherFragment()
    }

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val weatherViewModel: WeatherViewModel by viewModels()

    @SuppressLint("MissingPermission")
    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                fusedLocationClient.lastLocation.addOnSuccessListener {
                    if (it != null) {
                        weatherViewModel.changeLocationCoordinates(
                            it.latitude,
                            it.longitude,
                            WeatherPreferences.getInstance().getFahrenheitMode(requireContext())
                        )
                    }
                }
            } else {
                Toast.makeText(
                    requireContext(),
                    "Location weather update is unavailable! Default location selected",
                    Toast.LENGTH_LONG
                )
                weatherViewModel.updateWeather(
                    WeatherPreferences.getInstance().getCityId(requireContext()),
                    WeatherPreferences.getInstance().getFahrenheitMode(requireContext())
                )
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.weather_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loading.visibility = View.VISIBLE

        degreesTypeToggle.isChecked = WeatherPreferences.getInstance()
            .getFahrenheitMode(requireContext())

        updateWeatherViaCoordinates()

        degreesTypeToggle.setOnCheckedChangeListener { _: CompoundButton, _: Boolean ->
            Log.d(TAG, "Change temperature display mode!")
            Log.d(TAG, degreesTypeToggle.isChecked.toString())

            WeatherPreferences.getInstance()
                .setFahrenheitMode(requireContext(), degreesTypeToggle.isChecked)

            weatherViewModel.updateWeather(
                WeatherPreferences.getInstance().getCityId(requireContext()),
                degreesTypeToggle.isChecked
            )

        }

        changeCity.setOnClickListener {
            Log.d("DEBUG", "Display change city form")
            textInputLayout.visibility = View.VISIBLE
        }

        changeCityOk.setOnClickListener {
            Log.d("DEBUG", "Ok button pressed!")
            val cityLocation = changeCityInput.text
            loading.visibility = View.VISIBLE
            weatherViewModel.changeLocationCity(
                cityLocation.toString(),
                WeatherPreferences.getInstance().getFahrenheitMode(requireContext())
            )
            textInputLayout.visibility = View.GONE
        }

        myLocationIcon.setOnClickListener {
            loading.visibility = View.VISIBLE
            updateWeatherViaCoordinates()
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
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
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

    private fun updateWeatherViaCoordinates() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
            return
        }
        fusedLocationClient.lastLocation.addOnSuccessListener {
            if (it != null) {
                weatherViewModel.changeLocationCoordinates(
                    it.latitude,
                    it.longitude,
                    WeatherPreferences.getInstance().getFahrenheitMode(requireContext())
                )
            }
        }
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
}
