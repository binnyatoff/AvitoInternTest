package ru.binnyatoff.WeatherApp.data.model

import ru.binnyatoff.WeatherApp.data.network.modelsDaily.Temp

data class Daily(
    val dt: String?,
    val humidity: Int,
    val pressure: Int,
    val temp: Temp,
    val icon: String
)