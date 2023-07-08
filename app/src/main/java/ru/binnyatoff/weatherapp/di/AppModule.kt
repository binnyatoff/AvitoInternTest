package ru.binnyatoff.weatherapp.di

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.binnyatoff.weatherapp.App
import ru.binnyatoff.weatherapp.data.Repository
import ru.binnyatoff.weatherapp.screens.GPS
import ru.binnyatoff.weatherapp.screens.daily.viewmodels.DailyViewModelFactory
import ru.binnyatoff.weatherapp.screens.home.viewmodels.HomeViewModelFactory
import ru.binnyatoff.weatherapp.data.network.Api

@Module
class AppModule {

    @Provides
    fun provideGPS(context: Context): GPS {
        return GPS(context)
    }

    @Provides
    fun provideWeatherRepository(api: Api): Repository {
        return Repository(api)

    }

    @Provides
    fun provideMainViewModelFactory(repository: Repository, gps: GPS): HomeViewModelFactory {
        return HomeViewModelFactory(repository,gps)
    }


    @Provides
    fun provideDailyViewModelFactory(repository: Repository): DailyViewModelFactory {
        return DailyViewModelFactory(repository)
    }

}