package dev.denisnosoff.mamsytest.model.weather.entity

import com.google.gson.annotations.SerializedName

data class Main(
    @SerializedName("temp")
    val temp: Double
)