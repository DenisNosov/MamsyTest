package dev.denisnosoff.mamsytest.di.modules

import dagger.Module
import dagger.Provides
import dev.denisnosoff.mamsytest.model.cities.CitiesApiService
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class CitiesModule {

    private val BASE_URL = "http://api.openweathermap.org/"

    @Singleton
    @Provides
    fun provideCitiesApiService() : CitiesApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        return retrofit.create(CitiesApiService::class.java)
    }
}