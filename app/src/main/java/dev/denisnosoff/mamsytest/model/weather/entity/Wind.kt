package dev.denisnosoff.mamsytest.model.weather.entity

import com.google.gson.annotations.SerializedName

data class Wind(
    @SerializedName("speed")
    val speed: Double
)