package ru.binnyatoff.weatherappcompose.data

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import androidx.core.app.ActivityCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.binnyatoff.weatherapp.data.models.Coordinates
import ru.binnyatoff.weatherapp.data.toCoordinates

class GPS(private var appContext: Context) {

    private val locationManager =
        appContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    private val hasGps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    private val hasNetwork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

    private val _location = MutableSharedFlow<Coordinates>(
        replay = 1,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val location: SharedFlow<Coordinates> = _location.asSharedFlow()

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

            if (hasGps) {
                locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    600000,
                    4F
                ) { location ->
                    CoroutineScope(Dispatchers.IO).launch {
                        if (location != lastKnownLocationByGps) {
                            _location.emit(location.toCoordinates())
                        }
                    }
                }
            }

            if (hasNetwork) {
                locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    600000,
                    4F
                ) { location ->
                    CoroutineScope(Dispatchers.IO).launch {
                        if (location != lastKnownLocationByNetwork) {
                            _location.emit(location.toCoordinates())
                        }
                    }
                }
            }
        }
    }
}
