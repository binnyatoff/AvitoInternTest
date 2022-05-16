package ru.binnyatoff.WeatherApp.screens.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.binnyatoff.WeatherApp.data.WeatherRepository

class DailyViewModelFactory(var weatherRepository: WeatherRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        require(modelClass == DailyViewModel::class.java)
        return DailyViewModel(weatherRepository = weatherRepository) as T
    }
}