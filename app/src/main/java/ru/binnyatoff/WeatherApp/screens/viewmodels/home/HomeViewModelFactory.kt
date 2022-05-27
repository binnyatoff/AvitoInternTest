package ru.binnyatoff.WeatherApp.screens.viewmodels.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.binnyatoff.WeatherApp.data.WeatherRepository
import javax.inject.Inject


class HomeViewModelFactory @Inject constructor(private var weatherRepository: WeatherRepository) :
    ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        require(modelClass == HomeViewModel::class.java)
        return HomeViewModel(weatherRepository) as T
    }
}


