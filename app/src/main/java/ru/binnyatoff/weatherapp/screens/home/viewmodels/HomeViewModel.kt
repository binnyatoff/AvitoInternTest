package ru.binnyatoff.weatherapp.screens.home.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Response
import ru.binnyatoff.weatherapp.data.Repository
import ru.binnyatoff.weatherapp.data.models.Coordinates
import ru.binnyatoff.weatherapp.data.modelsDTO.CurrentWeatherDTO
import ru.binnyatoff.weatherapp.data.toCurrentWeather
import ru.binnyatoff.weatherapp.screens.home.HomeEvent
import ru.binnyatoff.weatherapp.screens.home.HomeState
import ru.binnyatoff.weatherapp.usecases.GpsCoordinatesUseCase
import java.text.SimpleDateFormat
import java.util.*

class HomeViewModel(
    private val weatherRepository: Repository,
    private val gpsCoordinatesUseCase: GpsCoordinatesUseCase,
) : ViewModel() {

    private val _state = MutableLiveData<HomeState>(HomeState.Loading)
    val state: LiveData<HomeState> = _state

    val c: Calendar = Calendar.getInstance()

    val year = c.get(Calendar.YEAR)
    val month = c.get(Calendar.MONTH)
    val day = c.get(Calendar.DAY_OF_MONTH)

    val hour = c.get(Calendar.HOUR_OF_DAY)
    val minute = c.get(Calendar.MINUTE)

    private var currentDate: String =
        SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())

    fun obtainEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.ScreenInit -> {
                getCoordinates()
            }
        }
    }

    private fun getCoordinates() {
        viewModelScope.launch {
            Log.e("TAG", "Coordinates")
            gpsCoordinatesUseCase.getLocate()
            gpsCoordinatesUseCase.coordinates.collect { coordinates ->
                getWeather(coordinates)
            }
        }
    }

    private fun getWeather(coordinates: Coordinates) {
        viewModelScope.launch {
            try {
                val response: Response<CurrentWeatherDTO> =
                    weatherRepository.getCurrentWeather(coordinates)
                Log.e("TAG", "$response")
                if (response.isSuccessful) {
                    val body = response.body()?.toCurrentWeather()
                    if (body != null) {
                        _state.postValue(
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
                        _state.postValue(HomeState.Empty)
                    }
                }
            } catch (e: Exception) {
                _state.postValue(HomeState.Error(e.toString()))
            }
        }
    }

    fun removeUpdatesLocationListener() {
        gpsCoordinatesUseCase.removeUpdatesLocationListener()
    }
}
