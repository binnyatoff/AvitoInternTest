package ru.binnyatoff.weatherapp.screens.home.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.binnyatoff.weatherapp.data.Repository
import ru.binnyatoff.weatherapp.usecases.GpsCoordinatesUseCase
import javax.inject.Inject


class HomeViewModelFactory @Inject constructor(
    private var weatherRepository: Repository,
    private var gpsCoordinatesUseCase: GpsCoordinatesUseCase,
) :
    ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        require(modelClass == HomeViewModel::class.java)
        return HomeViewModel(weatherRepository, gpsCoordinatesUseCase) as T
    }
}


