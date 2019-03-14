package dev.denisnosoff.mamsytest.di

import androidx.lifecycle.ViewModel
import dagger.Component
import dev.denisnosoff.mamsytest.di.modules.CitiesModule
import dev.denisnosoff.mamsytest.di.modules.ConverterModule
import dev.denisnosoff.mamsytest.searchactivity.SearchViewModel
import javax.inject.Singleton

@Singleton
@Component(modules = [CitiesModule::class, ConverterModule::class])
interface AppComponent {
    fun inject(searchViewModel: SearchViewModel)

}