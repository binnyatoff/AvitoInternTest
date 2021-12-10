package ru.binnyatoff.avitointerntest

object Constants {
    const val BASE_URL: String = "http://api.openweathermap.org/"
    private const val APP_ID:String = "a1b1d9d873a369c867978a5b0d661a5d"
    const val GET_WEATHER :String = "data/2.5/weather?q=Moscow,rus&APPID=$APP_ID&units=metric"
}