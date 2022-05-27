package ru.binnyatoff.WeatherApp.screens.viewmodels.daily

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.binnyatoff.WeatherApp.data.WeatherRepository

@Suppress("UNCHECKED_CAST")
class DailyViewModelFactory(private var weatherRepository: WeatherRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        require(modelClass == DailyViewModelFlow::class.java){}
        return DailyViewModelFlow(weatherRepository = weatherRepository) as T
    }
}