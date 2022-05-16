package ru.binnyatoff.WeatherApp.data.network

import retrofit2.Response
import retrofit2.http.*
import ru.binnyatoff.WeatherApp.data.model.WeatherDaily
import ru.binnyatoff.WeatherApp.data.network.modelsDaily.WeatherDailyDTO
import ru.binnyatoff.WeatherApp.data.network.modelsDay.Model

interface Api {
    @GET("data/2.5/weather?units=metric")
    suspend fun getWeather(@QueryMap coordinates: Map<String, Int>): Response<Model>

    @GET("data/2.5/onecall?units=metric&exclude=current,minutely,hourly,alerts")
    suspend fun getWeatherDaily(@QueryMap coordinates: Map<String, Int>) : Response<WeatherDailyDTO>

    @GET("data/2.5/weather?units=metric")
    suspend fun getCityWeather(@Query("q") city:String): Response<Model>
}