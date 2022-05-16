package ru.binnyatoff.WeatherApp.screens.home

sealed class HomeState {
    object Loading : HomeState()
    data class Loaded(
        val icon: String,
        val temp: Double,
        val humidity: Int,
        val wind: Double,
        val location: String,
        val currentTime: String
    ) : HomeState()

    object Empty : HomeState()
    data class Error(val error: String) : HomeState()
}
