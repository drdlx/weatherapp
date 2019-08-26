package com.example.weatherapp

import android.Manifest
import android.content.Context
import android.location.LocationManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.weatherapp.api.ApiClient
import com.example.weatherapp.ui.weather.WeatherFragment
import com.nabinbhandari.android.permissions.PermissionHandler
import com.nabinbhandari.android.permissions.Permissions
import java.util.ArrayList

class WeatherActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.weather_activity)

        Permissions.check(this, Manifest.permission.ACCESS_FINE_LOCATION, null, object: PermissionHandler() {
            override fun onGranted() {
                if (savedInstanceState == null) {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, WeatherFragment.newInstance())
                        .commitNow()
                }
            }
        })
    }

}
