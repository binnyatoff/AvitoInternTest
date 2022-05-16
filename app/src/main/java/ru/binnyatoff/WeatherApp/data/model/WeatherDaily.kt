package ru.binnyatoff.WeatherApp.data.model

data class WeatherDaily(
    val daily: List<Daily>,
    val lat: Int,
    val lon: Int,
    val timezone: String,
    val timezone_offset: Int
)