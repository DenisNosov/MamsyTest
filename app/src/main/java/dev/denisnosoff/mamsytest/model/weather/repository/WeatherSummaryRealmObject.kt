package dev.denisnosoff.mamsytest.model.weather.repository

import io.realm.RealmObject

open class WeatherSummaryRealmObject (
    var date: Long = 0,
    var temp: Double = 0.0,
    var description: String = "",
    var icon: String = "",
    var windSpeed: Double = 0.0
) : RealmObject() {
    override fun toString(): String {
        return "WeatherSummaryRealmObject(date=$date, temp=$temp, description='$description', icon='$icon', windSpeed=$windSpeed)"
    }
}