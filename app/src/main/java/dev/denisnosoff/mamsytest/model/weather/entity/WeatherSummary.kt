package dev.denisnosoff.mamsytest.model.weather.entity

import com.google.gson.annotations.SerializedName

data class WeatherSummary(
    @SerializedName("dt")
    val dt: Long,
    @SerializedName("main")
    val main: Main,
    @SerializedName("weather")
    val weather: List<Weather>,
    @SerializedName("wind")
    val wind: Wind
)