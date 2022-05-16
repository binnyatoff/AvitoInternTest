package ru.binnyatoff.WeatherApp.data.network.modelsDaily

data class WeatherDailyDTO(
    val daily: List<DailyDTO>,
    val lat: Int,
    val lon: Int,
    val timezone: String,
    val timezone_offset: Int
)