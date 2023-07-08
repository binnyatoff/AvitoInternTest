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

class DailyViewModel(private val repository: Repository) : ViewModel() {

    private val _weatherDailyList = MutableLiveData<List<DailyMap>>()
    val weatherDailyList: LiveData<List<DailyMap>> = _weatherDailyList


    private fun getLocate(){

    }

    private fun getWeatherDaily(coordinates: Coordinates) {
        viewModelScope.launch {
            try {
                val response = repository.getDailyWeather(coordinates)
                Log.e("TAG", "Response$response")
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        _weatherDailyList.postValue(body.toDailyMap())
                        Log.e("TAG", body.toString())
                    } else {
                        throw NullPointerException("body is null")
                    }
                } else {
                    Log.e(TAG, response.code().toString())
                }
            } catch (e: Exception) {
                Log.e("TAG", e.toString())
            }
        }
    }

    companion object {
        const val TAG = "TAG"
    }
}