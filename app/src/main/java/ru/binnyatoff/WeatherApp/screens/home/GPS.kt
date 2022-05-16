package ru.binnyatoff.WeatherApp.screens.home

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.core.app.ActivityCompat
import javax.inject.Inject

class GPS @Inject constructor(private var context: Context) {

    fun getLocate(): Location? {
        var locationByGps: Location? = null
        var locationByNetwork: Location? = null

        val locationManager =
            context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        val hasGps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        val hasNetwork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

        val gpsLocationListener =
            LocationListener { location -> locationByGps = location }

        val networkLocationListener =
            LocationListener { location -> locationByNetwork = location }

        val lastKnownLocationByGps =
            locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)

        val lastKnownLocationByNetwork =
            locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)


        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
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
        }
        return when {
            locationByGps != null -> {
                locationByGps
            }
            locationByNetwork != null -> {
                locationByNetwork
            }
            lastKnownLocationByGps != null -> {
                lastKnownLocationByGps
            }
            lastKnownLocationByNetwork != null -> {
                lastKnownLocationByNetwork
            }
            else -> {
                null
            }
        }
    }
}
