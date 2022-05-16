package ru.binnyatoff.WeatherApp.screens.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.binnyatoff.WeatherApp.data.network.Api
import javax.inject.Inject

class SearchViewModelFactory @Inject constructor(private var api: Api):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        require(modelClass == SearchViewModel::class.java)
        return SearchViewModel(api = api) as T
    }
}