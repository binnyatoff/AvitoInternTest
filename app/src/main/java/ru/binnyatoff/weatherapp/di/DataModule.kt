package ru.binnyatoff.weatherapp.di

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit
import ru.binnyatoff.weatherapp.BuildConfig
import ru.binnyatoff.weatherapp.data.network.AppInterceptor
import ru.binnyatoff.weatherapp.data.network.Api


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