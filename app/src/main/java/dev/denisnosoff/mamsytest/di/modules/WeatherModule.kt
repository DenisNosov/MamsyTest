package dev.denisnosoff.mamsytest.di.modules

import dagger.Module
import dagger.Provides
import dev.denisnosoff.mamsytest.model.weather.WeatherApiService
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class WeatherModule {

    private val BASE_URL = "https://api.openweathermap.org/"

    @Singleton
    @Provides
    fun provideWeatherApiService() : WeatherApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        return retrofit.create(WeatherApiService::class.java)
    }

}