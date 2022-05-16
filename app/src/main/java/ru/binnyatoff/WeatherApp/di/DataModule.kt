package ru.binnyatoff.WeatherApp.di

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit
import ru.binnyatoff.WeatherApp.BuildConfig
import ru.binnyatoff.WeatherApp.data.network.Api
import ru.binnyatoff.WeatherApp.data.network.AppInterceptor

@Module
class DataModule {

    @Provides
    fun provideRetrofit(): Retrofit {
        val httpClient = OkHttpClient.Builder()
            .addInterceptor(AppInterceptor(BuildConfig.API_KEY))
            .build()

        return Retrofit.Builder()
            .baseUrl("http://api.openweathermap.org/")
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideApi(retrofit: Retrofit): Api = retrofit.create(Api::class.java)
}