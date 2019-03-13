package dev.denisnosoff.mamsytest.mainactivity

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.denisnosoff.mamsytest.model.sharedpreferences.SharedPrefs

class MainViewModel(app: Application) : AndroidViewModel(app) {

    private val prefs: SharedPrefs = SharedPrefs(app)
    val citiesSet : MutableLiveData<Set<String>> = MutableLiveData()

    fun addCity(city: String) {

    }

    init {
//        citiesList.value = prefs.citiesSet ?:
    }

}