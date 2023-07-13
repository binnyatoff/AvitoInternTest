package ru.binnyatoff.weatherapp.screens.search.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.binnyatoff.weatherapp.data.modelsDTO.CurrentWeatherDTO
import ru.binnyatoff.weatherapp.data.network.Api

sealed class SearchViewState() {
    object Loading : SearchViewState()
    data class Loaded(
        val currentWeatherDTO: List<CurrentWeatherDTO>,
    ) : SearchViewState()

    object Empty : SearchViewState()
    data class Error(val error: String) : SearchViewState()
}

class SearchViewModel(private val api: Api) : ViewModel() {

    private val _currentWeather = MutableLiveData<SearchViewState>(SearchViewState.Empty)
    private val cityNameList: MutableList<String> = mutableListOf()

    private val currentWeatherDTOList: MutableList<CurrentWeatherDTO> = mutableListOf()
    val currentWeather: LiveData<SearchViewState> = _currentWeather

    fun getCityWeather(city: String) {
        _currentWeather.postValue(SearchViewState.Loading)
        if (city !in cityNameList) {
            cityNameList.add(city)
        }
        cityNameList.forEach { cityName ->
            viewModelScope.launch {
                try {
                    val response = api.getCityWeather(cityName)
                    Log.e("TAG", response.toString())
                    if (response.isSuccessful) {
                        val body = response.body()
                        if (body != null) {
                            if (body !in currentWeatherDTOList) {
                                currentWeatherDTOList.add(body)
                            }
                        }
                        Log.e("TAG", body.toString())
                    } else {
                        Log.e("TAG", "Error wtf ${response.code()}")
                    }
                } catch (e: Exception) {
                    Log.e("TAG", "Error connection")
                }
            }
        }
        _currentWeather.postValue(SearchViewState.Loaded(currentWeatherDTOList))
    }
}