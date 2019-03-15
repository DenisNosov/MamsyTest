package dev.denisnosoff.mamsytest.model.cities.entity

data class CitiesResult(
    val cod: String,
    val count: Int,
    val list: List<City>,
    val message: String
)