package ru.binnyatoff.weatherapp.screens.daily.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.binnyatoff.weatherapp.data.DailyMap
import ru.binnyatoff.weatherapp.data.Repository
import ru.binnyatoff.weatherapp.data.models.Coordinates
import ru.binnyatoff.weatherapp.data.toDailyMap
import ru.binnyatoff.weatherapp.screens.GPS
import java.lang.Error

sealed class DailyState() {
    object Loading : DailyState()
    data class Loaded(
        val data: List<DailyMap>,
    ) : DailyState()

    object Empty : DailyState()
    data class Error(val error: String) : DailyState()
}

class DailyViewModel(private val repository: Repository, private val gps: GPS) : ViewModel() {

    private val _state = MutableLiveData<DailyState>()
    val state: LiveData<DailyState> = _state

    init {
        getCoordinates()
    }

    private fun getCoordinates() {
        viewModelScope.launch {
            gps.getLocate()
            gps.coordinates.collect { coordinates ->
                Log.e("TAG", "$coordinates")
                getWeatherDaily(coordinates)
            }
        }
    }

    private fun getWeatherDaily(coordinates: Coordinates) {
        _state.postValue(DailyState.Loading)
        viewModelScope.launch {
            try {
                val response = repository.getDailyWeather(coordinates)
                Log.e("TAG", "Response$response")
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        val data = body.toDailyMap()
                        _state.postValue(
                            DailyState.Loaded(data)
                        )
                    } else {
                        throw NullPointerException("body is null")
                    }
                } else {
                    Log.e("Error response", response.code().toString())
                }
            } catch (e: Exception) {
                _state.postValue(DailyState.Error(e.toString()))
            }
        }
    }
}