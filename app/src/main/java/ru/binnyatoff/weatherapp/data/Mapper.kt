package ru.binnyatoff.weatherapp.data

import android.annotation.SuppressLint
import android.location.Location
import ru.binnyatoff.weatherapp.data.models.Coordinates
import ru.binnyatoff.weatherapp.data.models.CurrentWeather
import ru.binnyatoff.weatherapp.data.modelsDTO.CurrentWeatherDTO
import ru.binnyatoff.weatherapp.data.modelsDTO.Daily
import ru.binnyatoff.weatherapp.data.modelsDTO.DailyWeatherDTO
import ru.binnyatoff.weatherapp.data.modelsDTO.Temp
import java.text.SimpleDateFormat

@SuppressLint("SimpleDateFormat")
fun Int.toDateTime(): String {
    val dt = this
    val date = SimpleDateFormat("dd:MM:yyyy")
    return date.format(dt.toLong() * 1000)
}

fun String.toWeatherIcon():String{
    val id = this
    return "http://openweathermap.org/img/wn/${id}@2x.png"
}


fun Location.toCoordinates(): Coordinates {
    val location = this
    return Coordinates(location.latitude, location.longitude)
}

fun CurrentWeatherDTO.toCurrentWeather(): CurrentWeather {
    val currentWeatherDTO = this
    return CurrentWeather(
        icon = currentWeatherDTO.weather[0].icon,
        temp = currentWeatherDTO.main.temp,
        humidity = currentWeatherDTO.main.humidity,
        windSpeed = wind.speed,
        location = name,
        currentTime = timezone,
    )
}

data class DailyMap(
    val dt: String,
    val humidity: Int,
    val pressure: Int,
    val temp: Temp,
    val icon: String
)


fun Daily.toDailyMap(): DailyMap {
    val daily = this
    return DailyMap(
        dt = daily.dt.toDateTime(),
        humidity = daily.humidity,
        pressure = daily.pressure,
        temp = daily.temp,
        icon = daily.weather[0].icon.toWeatherIcon()
    )
}

fun DailyWeatherDTO.toDailyMap(): List<DailyMap> {
    val dailyWeatherDTO = this
    val dailyMap = dailyWeatherDTO.daily.map { daily ->
        daily.toDailyMap()
    }
    return dailyMap
}



