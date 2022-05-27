package ru.binnyatoff.WeatherApp.screens.viewmodels.daily

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import ru.binnyatoff.WeatherApp.data.WeatherRepository
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.binnyatoff.WeatherApp.data.model.Daily
import ru.binnyatoff.WeatherApp.data.toWeatherDaily


class DailyViewModelFlow(private val weatherRepository: WeatherRepository):ViewModel(){

    val weatherDailyList:MutableStateFlow<List<Daily>> = MutableStateFlow(emptyList())

    init {
        getWeatherDaily()
    }

    private fun getWeatherDaily() {
        viewModelScope.launch {
            try {
                val response = weatherRepository.getWeatherDaily()
                Log.e("TAG", "Response$response")
                if (response.isSuccessful) {
                    val body = response.body()?.toWeatherDaily()?.daily
                    if (body != null) {
                        weatherDailyList.value = body
                       // weatherDailyList.value(body)
                        Log.e("TAG", body.toString())
                    }
                }
            } catch (e: Exception) {
                Log.e("TAG", e.toString())
            }
        }
    }
    
}