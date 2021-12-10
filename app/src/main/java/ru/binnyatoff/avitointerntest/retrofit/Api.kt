package ru.binnyatoff.avitointerntest.retrofit
import retrofit2.Response
import retrofit2.http.GET
import ru.binnyatoff.avitointerntest.Constants
import ru.binnyatoff.avitointerntest.models.Model

interface Api {
    @GET(Constants.GET_WEATHER)
    suspend fun getWeather(): Response<Model>
}