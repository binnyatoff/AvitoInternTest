package ru.binnyatoff.WeatherApp.screens.viewmodels.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.binnyatoff.WeatherApp.data.WeatherRepository
import ru.binnyatoff.WeatherApp.data.toResult
import ru.binnyatoff.WeatherApp.screens.home.HomeState
import java.text.SimpleDateFormat
import java.util.*

class HomeViewModel(private val weatherRepository: WeatherRepository) : ViewModel() {

    val state = MutableLiveData<HomeState>(HomeState.Loading)
    private var currentDate: String = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())


    init {
        getWeather()
    }

    private fun getWeather() {
        viewModelScope.launch {
            try {
                val response = weatherRepository.getWeather()
                Log.e("TAG", "$response")
                if (response.isSuccessful) {
                    val body = response.body()?.toResult()
                    if (body != null) {
                        state.postValue(
                            HomeState.Loaded(
                                icon = body.icon,
                                temp = body.temp,
                                humidity = body.humidity,
                                wind = body.wind_speed,
                                location = body.city_name,
                                currentTime = currentDate
                            )
                        )
                    } else {
                        state.postValue(HomeState.Empty)
                    }
                }
            } catch (e: Exception) {
                state.postValue(HomeState.Error(e.toString()))
            }
        }
    }
}
