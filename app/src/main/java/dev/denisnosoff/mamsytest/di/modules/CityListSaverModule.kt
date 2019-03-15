package dev.denisnosoff.mamsytest.di.modules

import android.app.Application
import dagger.Module
import dagger.Provides
import dev.denisnosoff.mamsytest.App
import dev.denisnosoff.mamsytest.model.sharedpreferences.CityListSaver
import javax.inject.Singleton

@Module
class CityListSaverModule (private val appContext: Application) {

    @Singleton
    @Provides
    fun provideCityListSaver() : CityListSaver {
        return CityListSaver(appContext)
    }

}