package ru.binnyatoff.weatherapp.screens.daily.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.binnyatoff.weatherapp.data.Repository
import ru.binnyatoff.weatherapp.usecases.GpsCoordinatesUseCase

class DailyViewModelFactory(private var repository: Repository, private var gpsCoordinatesUseCase: GpsCoordinatesUseCase):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        require(modelClass == DailyViewModel::class.java){}
        return DailyViewModel(repository, gpsCoordinatesUseCase) as T
    }
}