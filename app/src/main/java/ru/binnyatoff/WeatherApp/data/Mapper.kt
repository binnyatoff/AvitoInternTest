package ru.binnyatoff.WeatherApp.data

import android.annotation.SuppressLint
import ru.binnyatoff.WeatherApp.data.model.Daily
import ru.binnyatoff.WeatherApp.data.model.WeatherDaily
import ru.binnyatoff.WeatherApp.data.model.WeatherResult
import ru.binnyatoff.WeatherApp.data.network.modelsDaily.DailyDTO
import ru.binnyatoff.WeatherApp.data.network.modelsDaily.WeatherDailyDTO
import ru.binnyatoff.WeatherApp.data.network.modelsDay.Model
import java.text.SimpleDateFormat

@SuppressLint("SimpleDateFormat")
fun getDateTime(s: Int): String {
    val sdf = SimpleDateFormat("dd:MM:yyyy")
    return sdf.format(s.toLong() * 1000)
}

fun ListDailyDTOtoDaily(listDTO: List<DailyDTO>) :List<Daily>{
    val list: MutableList<Daily> = mutableListOf()
    listDTO.forEach {
        list.add(it.toDaily())
    }
    return list
}


fun Model.toResult(): WeatherResult {
    return WeatherResult(
        temp = this.main.temp,
        humidity = this.main.humidity,
        wind_speed = this.wind.speed,
        city_name = this.name,
        icon = this.weather[0].icon
    )
}

fun DailyDTO.toDaily(): Daily {
    return Daily(
        dt = getDateTime(dt),
        humidity = humidity,
        pressure = pressure,
        temp = temp,
        icon = weather[0].icon
    )
}

fun WeatherDailyDTO.toWeatherDaily(): WeatherDaily {
    val listdaily = ListDailyDTOtoDaily(daily)
    return WeatherDaily(
        daily = listdaily,
        lat = lat,
        lon = lon,
        timezone = timezone,
        timezone_offset = timezone_offset
    )
}

