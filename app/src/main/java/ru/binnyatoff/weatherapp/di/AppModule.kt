package ru.binnyatoff.weatherapp.di

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.binnyatoff.weatherapp.data.Repository
import ru.binnyatoff.weatherapp.screens.daily.viewmodels.DailyViewModelFactory
import ru.binnyatoff.weatherapp.screens.home.viewmodels.HomeViewModelFactory
import ru.binnyatoff.weatherapp.data.network.Api
import ru.binnyatoff.weatherapp.usecases.GpsCoordinatesUseCase

@Module
class AppModule {

    @Provides
    fun provideGPS(context: Context): GpsCoordinatesUseCase {
        return GpsCoordinatesUseCase(context)
    }

    @Provides
    fun provideWeatherRepository(api: Api): Repository {
        return Repository(api)

    }

    @Provides
    fun provideMainViewModelFactory(
        repository: Repository,
        gpsCoordinatesUseCase: GpsCoordinatesUseCase,
    ): HomeViewModelFactory {
        return HomeViewModelFactory(repository, gpsCoordinatesUseCase)
    }


    @Provides
    fun provideDailyViewModelFactory(
        repository: Repository,
        gpsCoordinatesUseCase: GpsCoordinatesUseCase,
    ): DailyViewModelFactory {
        return DailyViewModelFactory(repository, gpsCoordinatesUseCase)
    }

}