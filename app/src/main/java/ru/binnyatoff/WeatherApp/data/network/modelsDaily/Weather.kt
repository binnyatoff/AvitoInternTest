package ru.binnyatoff.WeatherApp.data.network.modelsDaily

data class Weather(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)