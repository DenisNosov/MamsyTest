package dev.denisnosoff.mamsytest.model.weather.repository

import io.reactivex.Flowable
import io.realm.Realm
import io.realm.kotlin.where

class WeatherRepository {

    private val realm = Realm.getDefaultInstance()

    fun saveData(weatherRealmObject: WeatherRealmObject) {
        realm.executeTransaction {
            it.copyToRealmOrUpdate(weatherRealmObject)
        }
    }

    fun getData(id: Int) : Flowable<WeatherRealmObject> {
        return realm.where<WeatherRealmObject>()
            .equalTo("cityId", id)
            .findFirstAsync()
            .asFlowable<WeatherRealmObject>()
    }

    fun close() {
        realm.close()
    }
}