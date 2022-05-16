package ru.binnyatoff.WeatherApp.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.binnyatoff.WeatherApp.screens.home.GPS
import ru.binnyatoff.WeatherApp.screens.home.HomeFragment
import ru.binnyatoff.WeatherApp.screens.search.SearchFragment
import ru.binnyatoff.WeatherApp.screens.daily.DailyFragment

@Component(modules = [AppModule::class, DataModule::class])
interface AppComponent {
    fun inject(homeFragment: HomeFragment)
    fun inject(searchFragment: SearchFragment)
    fun inject(gps: GPS)
    fun inject(dailyFragment: DailyFragment)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(context: Context): Builder
        fun build(): AppComponent
    }
}