package dev.denisnosoff.mamsytest.model.weather.entity

import dev.denisnosoff.mamsytest.model.cities.CityItem

data class WeatherRequestResult(
    val city: CityItem,
    val list: List<WeatherSummary>
)