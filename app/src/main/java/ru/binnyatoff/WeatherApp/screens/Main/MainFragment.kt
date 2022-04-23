package ru.binnyatoff.WeatherApp.screens.Main

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.binnyatoff.WeatherApp.R
import kotlin.math.floor

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {
    private val viewModel: MainViewModel by viewModels()
    private var currentLocation: Location? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val temp: TextView = view.findViewById(R.id.temp)
        val humidity: TextView = view.findViewById(R.id.humidity)
        val wind: TextView = view.findViewById(R.id.wind)
        val location: TextView = view.findViewById(R.id.location)
        getLocate()
        val coordinates: Map<String, Double> = mapOf(
            "lat" to currentLocation!!.latitude,
            "lon" to currentLocation!!.longitude
        )
        Log.e("TAG", coordinates.toString())
        viewModel.getWeather(coordinates)

        viewModel.temp.observe(viewLifecycleOwner) {
            temp.text = "${floor(it).toInt()}"
        }
        viewModel.humidity.observe(viewLifecycleOwner) {
            humidity.text = it.toString()
        }
        viewModel.wind.observe(viewLifecycleOwner) {
            wind.text = it.toString()
        }
        viewModel.location.observe(viewLifecycleOwner) {
            location.text = it.toString()
        }

    }

    private fun checkSelfPermission() {
        val requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {

            } else {
            }
        }
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            Log.e("TAG", "Granted in get location")
        } else {
            Log.e("TAG", "Denied in get location")
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    private fun getLocate() {
        var locationByGps: Location? = null
        var locationByNetwork: Location? = null
        val locationManager =
            requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val hasGps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        val hasNetwork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

        val gpsLocationListener: LocationListener = object : LocationListener {
            override fun onLocationChanged(location: Location) {
                locationByGps = location

            }
        }

        val networkLocationListener: LocationListener = object : LocationListener {
            override fun onLocationChanged(location: Location) {
                locationByNetwork = location
            }

        }

        checkSelfPermission()

        if (hasGps) {
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                5000,
                0F,
                gpsLocationListener
            )
        }

        if (hasNetwork) {
            locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                5000,
                0F,
                networkLocationListener
            )
        }

        val lastKnownLocationByGps =
            locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        lastKnownLocationByGps?.let {
            locationByGps = lastKnownLocationByGps
        }
        val lastKnownLocationByNetwork =
            locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
        lastKnownLocationByNetwork?.let {
            locationByNetwork = lastKnownLocationByNetwork
        }
        currentLocation = locationByGps
/*
        if (locationByGps != null && locationByNetwork != null) {
            if (locationByGps!!.accuracy > locationByNetwork!!.accuracy) {
                currentLocation = locationByGps
                Log.e("TAG", "By GPS")
            } else {
                currentLocation = locationByGps
                Log.e("TAG", "By Network")
            }
        }*/
    }
}