package ru.binnyatoff.WeatherApp.data.model

data class WeatherResult(
        val temp: Double,
        val humidity: Int,
        val wind_speed: Double,
        val city_name:String,
        val icon:String
)