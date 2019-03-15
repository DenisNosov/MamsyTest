package dev.denisnosoff.mamsytest.model.weather.entity

data class X(
    val dt: Int,
    val dt_txt: String,
    val main: Main,
    val weather: List<Weather>,
    val wind: Wind
)