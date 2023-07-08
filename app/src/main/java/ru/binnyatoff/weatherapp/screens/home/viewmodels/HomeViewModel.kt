package ru.binnyatoff.weatherapp.screens.home.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Response
import ru.binnyatoff.weatherapp.data.Repository
import ru.binnyatoff.weatherapp.data.models.Coordinates
import ru.binnyatoff.weatherapp.data.modelsDTO.CurrentWeatherDTO
import ru.binnyatoff.weatherapp.data.toCurrentWeather
import ru.binnyatoff.weatherapp.screens.GPS
import ru.binnyatoff.weatherapp.screens.home.HomeState
import java.text.SimpleDateFormat
import java.util.*

class HomeViewModel(private val weatherRepository: Repository, private val gps: GPS) : ViewModel() {

    val state = MutableLiveData<HomeState>(HomeState.Loading)
    private var currentDate: String =
        SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())

    init {
        getCoordinates()
    }

    private fun getCoordinates() {
        viewModelScope.launch {
            gps.getLocate()
            gps.coordinates.collect { coordinates ->
                Log.e("TAG", "$coordinates")
                getWeather(coordinates)
            }
        }
    }

    private suspend fun getWeather(coordinates: Coordinates) {
        try {
            val response: Response<CurrentWeatherDTO> =
                weatherRepository.getCurrentWeather(coordinates)
            Log.e("TAG", "$response")
            if (response.isSuccessful) {
                val body = response.body()?.toCurrentWeather()
                if (body != null) {
                    state.postValue(
                        HomeState.Loaded(
                            icon = body.icon,
                            temp = body.temp,
                            humidity = body.humidity,
                            wind = body.windSpeed,
                            location = body.location,
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

    fun removeUpdatesLocationListener(){
        gps.removeUpdatesLocationListener()
    }
}
