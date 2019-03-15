package dev.denisnosoff.mamsytest

import android.app.Application
import dev.denisnosoff.mamsytest.di.AppComponent
import dev.denisnosoff.mamsytest.di.DaggerAppComponent
import dev.denisnosoff.mamsytest.di.modules.CitiesModule
import dev.denisnosoff.mamsytest.di.modules.CityListSaverModule
import dev.denisnosoff.mamsytest.di.modules.ConverterModule

class App : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .cityListSaverModule(CityListSaverModule(this))
            .converterModule(ConverterModule())
            .citiesModule(CitiesModule())
            .build()
    }
}