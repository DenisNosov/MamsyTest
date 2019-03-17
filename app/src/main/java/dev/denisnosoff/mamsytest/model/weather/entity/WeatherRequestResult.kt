package dev.denisnosoff.mamsytest.model.weather.entity

import com.google.gson.annotations.SerializedName
import dev.denisnosoff.mamsytest.model.cities.CityItem

data class WeatherRequestResult(
    @SerializedName("city")
    val city: CityItem,
    @SerializedName("list")
    val list: List<WeatherSummary>
)