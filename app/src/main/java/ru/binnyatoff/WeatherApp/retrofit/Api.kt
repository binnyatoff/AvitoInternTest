package ru.binnyatoff.WeatherApp.retrofit
import retrofit2.Response
import retrofit2.http.*
import ru.binnyatoff.WeatherApp.Constants
import ru.binnyatoff.WeatherApp.models.Model

interface Api {
    @GET("data/2.5/weather?APPID=${Constants.APP_ID}&units=metric")
    suspend fun getWeather(@QueryMap coordinates: Map<String, Double>): Response<Model>

    @GET("data/2.5/weather?APPID=${Constants.APP_ID}&units=metric")
    suspend fun getCityWeather(@Query("q") city:String): Response<Model>

    /*@GET("users/{user}/repos")
    fun listRepos(@Path("user") user: String?): Response<Model>*/

}