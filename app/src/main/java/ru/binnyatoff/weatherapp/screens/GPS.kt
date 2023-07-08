package ru.binnyatoff.weatherapp.screens

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationListener
import android.location.LocationManager
import android.util.Log
import androidx.core.app.ActivityCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import ru.binnyatoff.weatherapp.data.models.Coordinates
import ru.binnyatoff.weatherapp.data.toCoordinates
import javax.inject.Inject

class GPS @Inject constructor(private var context: Context) {


    private val _coordinates = MutableSharedFlow<Coordinates>(
        replay = 1,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    val coordinates: SharedFlow<Coordinates> = _coordinates.asSharedFlow()

    private val locationManager =
        context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    private val hasGps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    private val hasNetwork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)


    private val locationListener: LocationListener = LocationListener { location ->
        CoroutineScope(Dispatchers.IO).launch {
            _coordinates.emit(location.toCoordinates())
        }
    }


    fun getLocate() {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            if (hasGps) {
                if (locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER) != null) {
                    CoroutineScope(Dispatchers.IO).launch {
                        _coordinates.emit(
                            locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)!!
                                .toCoordinates()
                        )
                    }
                    Log.e("TAG", "NOT NULL")
                } else {
                    Log.e("TAG", "NULL")
                    locationManager.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER,
                        5000,
                        1F,
                        locationListener
                    )
                }
            }
            if (hasNetwork) {
                if (locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER) != null) {
                    CoroutineScope(Dispatchers.IO).launch {
                        _coordinates.emit(
                            locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)!!
                                .toCoordinates()
                        )
                    }
                } else {
                    locationManager.requestLocationUpdates(
                        LocationManager.NETWORK_PROVIDER,
                        5000,
                        1F,
                        locationListener
                    )
                }
            }
        }
    }

    fun removeUpdatesLocationListener() {
        locationManager.removeUpdates(locationListener)
    }
}


