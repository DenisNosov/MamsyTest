package dev.denisnosoff.mamsytest.di

import dagger.Component
import dev.denisnosoff.mamsytest.di.modules.CitiesModule
import dev.denisnosoff.mamsytest.di.modules.CityListSaverModule
import dev.denisnosoff.mamsytest.di.modules.ConverterModule
import dev.denisnosoff.mamsytest.mainactivity.MainViewModel
import dev.denisnosoff.mamsytest.model.sharedpreferences.CityListSaver
import dev.denisnosoff.mamsytest.searchactivity.SearchViewModel
import javax.inject.Singleton

@Singleton
@Component(modules = [CitiesModule::class, ConverterModule::class, CityListSaverModule::class])
interface AppComponent {
    fun inject(searchViewModel: SearchViewModel)
    fun inject(mainViewModel: MainViewModel)
    fun inject(cityListSaver: CityListSaver)
}