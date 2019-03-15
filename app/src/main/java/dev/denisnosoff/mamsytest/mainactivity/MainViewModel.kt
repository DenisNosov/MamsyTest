package dev.denisnosoff.mamsytest.mainactivity

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import dev.denisnosoff.mamsytest.App
import dev.denisnosoff.mamsytest.model.cities.CityItem
import dev.denisnosoff.mamsytest.model.sharedpreferences.CityListSaver
import javax.inject.Inject

class MainViewModel(app: Application) : AndroidViewModel(app) {

    private val TAG = "MainViewModel"

    @Inject
    lateinit var cityListSaver: CityListSaver

    val citiesListLiveData : MutableLiveData<ArrayList<CityItem>> = MutableLiveData()

    init {
        (app as App).appComponent.inject(this)
        citiesListLiveData.value = cityListSaver.citiesList
    }

    fun addCity(city: CityItem) {
        Log.d(TAG, "adding city $city")
        val newList = citiesListLiveData.value
        newList?.add(city)
        citiesListLiveData.value = newList
    }

    fun removeCity(city: CityItem?) {
        val newList = citiesListLiveData.value
        newList?.remove(city)
        citiesListLiveData.value = newList
    }

    fun onPause() {
        cityListSaver.citiesList = citiesListLiveData.value!!
    }
}