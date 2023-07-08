package ru.binnyatoff.weatherapp.data.models

data class CurrentWeather(
    val icon: String,
    val temp: Double,
    val humidity: Int,
    val windSpeed: Double,
    val location: String,
    val currentTime: Int
)