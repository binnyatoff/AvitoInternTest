package ru.binnyatoff.WeatherApp.data

import android.location.Location
import android.util.Log
import retrofit2.Response
import ru.binnyatoff.WeatherApp.data.network.Api
import ru.binnyatoff.WeatherApp.data.network.modelsDaily.WeatherDailyDTO
import ru.binnyatoff.WeatherApp.data.network.modelsDay.Model
import ru.binnyatoff.WeatherApp.screens.home.GPS

class WeatherRepository(val api:Api, private val gps: GPS) {

   suspend fun getWeather(): Response<Model> {
        val coordinates = getCoordinates(gps.getLocate())
        return api.getWeather(coordinates)
    }

    suspend fun getWeatherDaily(): Response<WeatherDailyDTO>{
        val coordinates = getCoordinates(gps.getLocate())
        Log.e("TAG", coordinates.toString())
        return api.getWeatherDaily(coordinates)
    }

    private fun getCoordinates(currentLocation: Location?): Map<String, Int> {
        return mapOf(
            "lat" to (currentLocation?.latitude?.toInt() ?: 0),
            "lon" to (currentLocation?.longitude?.toInt() ?: 0)
        )
    }
}