package ru.binnyatoff.WeatherApp.di

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.binnyatoff.WeatherApp.data.WeatherRepository
import ru.binnyatoff.WeatherApp.data.network.Api
import ru.binnyatoff.WeatherApp.screens.home.GPS
import ru.binnyatoff.WeatherApp.screens.viewmodels.daily.DailyViewModelFactory
import ru.binnyatoff.WeatherApp.screens.viewmodels.home.HomeViewModelFactory

@Module
class AppModule {

    @Provides
    fun provideGPS(context: Context): GPS {
        return GPS(context)
    }

    @Provides
    fun provideWeatherRepository(api: Api, gps: GPS): WeatherRepository {
        return WeatherRepository(api, gps)

    }

    @Provides
    fun provideMainViewModelFactory(weatherRepository: WeatherRepository): HomeViewModelFactory {
        return HomeViewModelFactory(weatherRepository)
    }


    @Provides
    fun provideDailyViewModelFactory(weatherRepository: WeatherRepository): DailyViewModelFactory {
        return DailyViewModelFactory(weatherRepository)
    }
}