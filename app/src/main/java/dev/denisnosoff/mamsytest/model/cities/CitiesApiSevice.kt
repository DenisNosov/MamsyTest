package dev.denisnosoff.mamsytest.model.cities

import dev.denisnosoff.mamsytest.model.cities.entity.CitiesResult
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CitiesApiSevice {

    @GET("data/2.5/find?mode=json&type=like")
    fun getCitiesList(@Query("q") cityName: String,
                      @Query("appId") apiKey: String,
                      @Query("mode") mode: String = "json",
                      @Query("type") type: String = "like",
                      @Query("cnt") count: String = "15") :
            Observable<CitiesResult>

}