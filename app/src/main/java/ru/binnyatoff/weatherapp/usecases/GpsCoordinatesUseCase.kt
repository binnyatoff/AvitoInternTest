package ru.binnyatoff.weatherapp.usecases

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import androidx.core.app.ActivityCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.binnyatoff.weatherapp.data.models.Coordinates
import ru.binnyatoff.weatherapp.data.toCoordinates
import javax.inject.Inject


class GpsCoordinatesUseCase @Inject constructor(private var appContext: Context) {

    private val locationManager =
        appContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    private val hasGps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    private val hasNetwork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

    private val _coordinates = MutableSharedFlow<Coordinates>(
        replay = 1,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val coordinates: SharedFlow<Coordinates> = _coordinates.asSharedFlow()

    private val locationListener: LocationListener = object : LocationListener {

        override fun onLocationChanged(location: Location) {
            CoroutineScope(Dispatchers.IO).launch {
                _coordinates.emit(location.toCoordinates())
            }
        }

        @Deprecated("Deprecated in Java")
        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        }

        override fun onProviderEnabled(provider: String) {}

        override fun onProviderDisabled(provider: String) {}
    }

    fun getLocate() {
        if (ActivityCompat.checkSelfPermission(
                appContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                appContext,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {

            val lastKnownLocationByGps =
                locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)

            val lastKnownLocationByNetwork =
                locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)

            if (lastKnownLocationByGps != null) {
                CoroutineScope(Dispatchers.IO).launch {
                    _coordinates.emit(lastKnownLocationByGps.toCoordinates())
                }
            } else {
                if (lastKnownLocationByNetwork != null) {
                    CoroutineScope(Dispatchers.IO).launch {
                        _coordinates.emit(lastKnownLocationByNetwork.toCoordinates())
                    }
                } else {
                    if (hasGps) {
                        locationManager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER,
                            600000,
                            4F,
                            locationListener
                        )
                    }

                    if (hasNetwork) {
                        locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            600000,
                            4F,
                            locationListener
                        )
                    }
                }
            }

        }
    }

    fun removeUpdatesLocationListener() {
        locationManager.removeUpdates(locationListener)
    }
}
