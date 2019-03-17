package dev.denisnosoff.mamsytest

import android.app.Application
import dev.denisnosoff.mamsytest.di.AppComponent
import dev.denisnosoff.mamsytest.di.DaggerAppComponent
import dev.denisnosoff.mamsytest.di.modules.*
import io.realm.Realm

class App : Application() {

    private lateinit var realm: Realm
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        appComponent = DaggerAppComponent.builder()
            .weatherRepositoryModule(WeatherRepositoryModule())
            .weatherModule(WeatherModule())
            .cityListSaverModule(CityListSaverModule(this))
            .converterModule(ConverterModule())
            .citiesModule(CitiesModule())
            .build()
    }

    companion object {
        const val API_KEY = "6382139bc73c4a4a05d340bc54b53606"
    }
}