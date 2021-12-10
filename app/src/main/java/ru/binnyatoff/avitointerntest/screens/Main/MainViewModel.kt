package ru.binnyatoff.avitointerntest.screens.Main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.binnyatoff.avitointerntest.models.Model
import ru.binnyatoff.avitointerntest.retrofit.Api
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private  val api: Api) :ViewModel() {
    val temperature = MutableLiveData<Double>()

    init {
        getWeather()
    }

    private fun getWeather() {
        viewModelScope.launch {
            try {
                val response = api.getWeather()
                if (response.isSuccessful) {
                    temperature.postValue(response.body()?.main?.temp)
                    Log.e("TAG", temperature.toString())

                }
            } catch (e: Exception){
                Log.e("TAG", "Error connection")
            }
        }
    }
}