package ru.binnyatoff.weatherapp.data.modelsDTO

data class CurrentWeatherDTO(
    val main: Main,
    val name: String,
    val timezone: Int,
    val weather: List<Weather>,
    val wind: Wind
)

data class Main(
    val humidity: Int,
    val temp: Double,
    val temp_max: Double,
    val temp_min: Double
)

data class Weather(
    val icon: String
)

data class Wind(
    val speed: Double
)