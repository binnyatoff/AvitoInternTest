package ru.binnyatoff.avitointerntest.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit
import ru.binnyatoff.avitointerntest.Constants
import ru.binnyatoff.avitointerntest.retrofit.Api
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class Retrofit {

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor():HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BASIC}

    @Provides
    @Singleton
    fun provideOkHttpClient(httpLoggingInterceptor:HttpLoggingInterceptor): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(httpLoggingInterceptor)
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): Api = retrofit.create(Api::class.java)
}