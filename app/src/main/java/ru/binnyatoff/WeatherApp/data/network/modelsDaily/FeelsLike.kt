package ru.binnyatoff.WeatherApp.data.network.modelsDaily

data class FeelsLike(
    val day: Double,
    val eve: Double,
    val morn: Double,
    val night: Double
)