package dev.denisnosoff.mamsytest.model.weather.entity

import dev.denisnosoff.mamsytest.model.cities.CityItem

data class WeatherResult(
    val city: CityItem,
    val list: List<X>
)