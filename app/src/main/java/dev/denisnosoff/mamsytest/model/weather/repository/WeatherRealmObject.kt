package dev.denisnosoff.mamsytest.model.weather.repository

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class WeatherRealmObject(
    @PrimaryKey var cityId: Int = 0,
    var weatherSummaries: RealmList<WeatherSummaryRealmObject> = RealmList()
) : RealmObject()