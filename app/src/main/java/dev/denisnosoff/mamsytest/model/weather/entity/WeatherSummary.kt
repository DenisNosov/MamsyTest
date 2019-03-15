package dev.denisnosoff.mamsytest.model.weather.entity

data class WeatherSummary(
    val dt: Int,
    val dt_txt: String,
    val main: Main,
    val weather: List<Weather>,
    val wind: Wind
)