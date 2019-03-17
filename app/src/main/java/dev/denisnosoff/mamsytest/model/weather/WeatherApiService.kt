package dev.denisnosoff.mamsytest.model.weather

import dev.denisnosoff.mamsytest.model.weather.entity.WeatherRequestResult
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {

    @GET("data/2.5/forecast")
    fun getWeatherById(@Query("id") id: String,
                       @Query("units") units: String = "metric",
                       @Query("lang") lang: String = "en",
                       @Query("appId") apiKey: String
                       ) :
            Observable<WeatherRequestResult>

}