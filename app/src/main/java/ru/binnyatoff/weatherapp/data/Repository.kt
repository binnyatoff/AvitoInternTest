package ru.binnyatoff.weatherapp.data

import retrofit2.Response
import ru.binnyatoff.weatherapp.data.models.Coordinates
import ru.binnyatoff.weatherapp.data.modelsDTO.CurrentWeatherDTO
import ru.binnyatoff.weatherapp.data.modelsDTO.DailyWeatherDTO
import ru.binnyatoff.weatherapp.data.network.Api
import javax.inject.Inject

class Repository @Inject constructor(private val api: Api) {

    suspend fun getCurrentWeather(coordinates: Coordinates): Response<CurrentWeatherDTO> {
        val map = mapOf(
            "lat" to (coordinates.latitude),
            "lon" to (coordinates.longitude)
        )
        return api.getCurrentWeather(map)
    }

    suspend fun getDailyWeather(coordinates: Coordinates): Response<DailyWeatherDTO> {
        val map = mapOf(
            "lat" to (coordinates.latitude),
            "lon" to (coordinates.longitude)
        )
        return api.getWeatherDaily(map)
    }

    suspend fun getCityWeather(city: String): Response<CurrentWeatherDTO> {
        return api.getCityWeather(city = city)
    }
}

