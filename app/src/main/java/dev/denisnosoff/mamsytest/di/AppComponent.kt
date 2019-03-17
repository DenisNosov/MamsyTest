package dev.denisnosoff.mamsytest.di

import dagger.Component
import dev.denisnosoff.mamsytest.di.modules.*
import dev.denisnosoff.mamsytest.mainactivity.MainViewModel
import dev.denisnosoff.mamsytest.model.sharedpreferences.CityListSaver
import dev.denisnosoff.mamsytest.searchactivity.SearchViewModel
import dev.denisnosoff.mamsytest.weatherfragment.WeatherViewModel
import javax.inject.Singleton

@Singleton
@Component(modules = [
    CitiesModule::class,
    ConverterModule::class,
    CityListSaverModule::class,
    WeatherModule::class,
    WeatherRepositoryModule::class
])
interface AppComponent {
    fun inject(searchViewModel: SearchViewModel)
    fun inject(mainViewModel: MainViewModel)
    fun inject(cityListSaver: CityListSaver)
    fun inject(weatherViewModel: WeatherViewModel)
}