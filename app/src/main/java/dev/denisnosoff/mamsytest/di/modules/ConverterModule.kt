package dev.denisnosoff.mamsytest.di.modules

import dagger.Module
import dagger.Provides
import dev.denisnosoff.mamsytest.util.CityToCityItem

@Module
class ConverterModule {

    @Provides
    fun provideCityToCityItemConverter() : CityToCityItem {
        return CityToCityItem()
    }

}