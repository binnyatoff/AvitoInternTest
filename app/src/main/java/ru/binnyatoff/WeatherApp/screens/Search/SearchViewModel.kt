package ru.binnyatoff.WeatherApp.screens.Search

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.binnyatoff.WeatherApp.retrofit.Api
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val api:Api): ViewModel() {
    val temp = MutableLiveData<Double>()
    val humidity = MutableLiveData<Int>()
    val wind = MutableLiveData<Double>()
    val location = MutableLiveData<String>()

    fun getCityWeather(city:String){
        viewModelScope.launch {
            Log.e("TAG", city)
            try{
                val response = api.getCityWeather(city)
                Log.e("TAG", response.toString())
                if (response.isSuccessful){
                    val body = response.body()
                    temp.postValue(body?.main?.temp) // температура
                    humidity.postValue(body?.main?.humidity)//влажность
                    wind.postValue(body?.wind?.speed)//
                    location.postValue(body?.name)//название города
                    Log.e("TAG", body.toString())
                }
            }
            catch (e:Exception){
                Log.e("TAG", "Error connection")
            }
        }
    }
}