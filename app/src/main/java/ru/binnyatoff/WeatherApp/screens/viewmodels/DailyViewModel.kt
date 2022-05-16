package ru.binnyatoff.WeatherApp.screens.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.binnyatoff.WeatherApp.data.WeatherRepository
import ru.binnyatoff.WeatherApp.data.model.Daily
import ru.binnyatoff.WeatherApp.data.network.toWeatherDaily

class DailyViewModel(private val weatherRepository: WeatherRepository) : ViewModel() {

    val weatherDailyList = MutableLiveData<List<Daily>>()

    init {
        getWeatherDaily()
    }

    private fun getWeatherDaily() {
        viewModelScope.launch {
            try {
                val response = weatherRepository.getWeatherDaily()
                Log.e("TAG", "Response" + response.toString())
                if (response.isSuccessful) {
                    val body = response.body()?.toWeatherDaily()?.daily
                    if (body != null) {
                        weatherDailyList.postValue(body!!)
                        Log.e("TAG", body.toString())

                    }
                }
            } catch (e: Exception) {
                Log.e("TAG", e.toString())
            }
        }
    }
}