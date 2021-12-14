package ru.binnyatoff.avitointerntest.retrofit
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import ru.binnyatoff.avitointerntest.Constants
import ru.binnyatoff.avitointerntest.models.Model

interface Api {
    @GET(Constants.GET_WEATHER)
    suspend fun getWeather(): Response<Model>

    @GET("data/2.5/weather?APPID=${Constants.APP_ID}&units=metric")
    suspend fun getCityWeather(@Query("q") city:String): Response<Model>

    /*@GET("users/{user}/repos")
    fun listRepos(@Path("user") user: String?): Response<Model>*/

}