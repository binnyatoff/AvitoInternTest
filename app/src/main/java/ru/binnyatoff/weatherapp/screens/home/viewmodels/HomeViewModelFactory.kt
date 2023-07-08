package ru.binnyatoff.weatherapp.screens.home.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.binnyatoff.weatherapp.data.Repository
import ru.binnyatoff.weatherapp.screens.GPS
import javax.inject.Inject


class HomeViewModelFactory @Inject constructor(
    private var weatherRepository: Repository,
    private var gps: GPS,
) :
    ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        require(modelClass == HomeViewModel::class.java)
        return HomeViewModel(weatherRepository, gps) as T
    }
}


