package ru.binnyatoff.weatherapp.di


import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.binnyatoff.weatherapp.screens.daily.DailyFragment
import ru.binnyatoff.weatherapp.screens.home.HomeFragment
import ru.binnyatoff.weatherapp.screens.search.SearchFragment
import ru.binnyatoff.weatherappcompose.data.GPS


@Component(modules = [AppModule::class, DataModule::class])
interface AppComponent {
    fun inject(homeFragment: HomeFragment)
    fun inject(searchFragment: SearchFragment)
    fun inject(dailyFragment: DailyFragment)
    fun inject(gps: GPS)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(context: Context): Builder
        fun build(): AppComponent
    }

}
