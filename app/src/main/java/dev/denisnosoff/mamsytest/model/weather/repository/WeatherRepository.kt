package dev.denisnosoff.mamsytest.model.weather.repository

import io.reactivex.Flowable
import io.realm.Realm
import io.realm.RealmResults
import io.realm.kotlin.createObject
import io.realm.kotlin.where

class WeatherRepository {

    private lateinit var realm: Realm

    fun saveData(weatherRealmObject: WeatherRealmObject) {
        realm.executeTransaction {
            realm.copyToRealmOrUpdate(weatherRealmObject)
        }
    }

    fun getData(id: Int) : WeatherRealmObject {
        val realmResult = realm.where(WeatherRealmObject::class.java)
            .equalTo("cityId", id)
            .findAllAsync()
        return realm.copyFromRealm(realmResult[0]!!)
    }

    fun close() {
        realm.close()
    }

    fun init() {
        realm = Realm.getDefaultInstance()
    }
}