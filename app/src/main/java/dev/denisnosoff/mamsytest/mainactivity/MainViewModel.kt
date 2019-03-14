package dev.denisnosoff.mamsytest.mainactivity

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import dev.denisnosoff.mamsytest.model.sharedpreferences.SharedPrefs

class MainViewModel(app: Application) : AndroidViewModel(app) {

    private val TAG = "MainViewModel"

    private val prefs: SharedPrefs = SharedPrefs(app)
    val citiesListLiveData : MutableLiveData<ArrayList<String>> = MutableLiveData()

    init {
        citiesListLiveData.value = prefs.citiesList
    }

    fun addCity(city: String) {
        Log.d(TAG, "adding city $city")
        val newList = citiesListLiveData.value
        newList?.add(city)
        citiesListLiveData.value = newList
    }

    fun onPause() {
        prefs.citiesList = citiesListLiveData.value
    }
}