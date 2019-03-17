package dev.denisnosoff.mamsytest.di.modules

import dagger.Module
import dagger.Provides
import dev.denisnosoff.mamsytest.model.weather.repository.WeatherRepository
import javax.inject.Singleton

@Module
class WeatherRepositoryModule {

    @Singleton
    @Provides
    fun providesWeatherRepository() = WeatherRepository()

}