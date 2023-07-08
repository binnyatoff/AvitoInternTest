package ru.binnyatoff.weatherapp

import android.app.Application
import android.content.Context
import ru.binnyatoff.weatherapp.di.AppComponent
import ru.binnyatoff.weatherapp.di.DaggerAppComponent

class App : Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent
            .builder()
            .context(this)
            .build()

    }
}

val Context.appComponent: AppComponent
    get() = when (this) {
        is App -> appComponent
        else -> this.applicationContext.appComponent
    }