package dev.denisnosoff.mamsytest.searchactivity

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import dev.denisnosoff.mamsytest.App
import dev.denisnosoff.mamsytest.model.cities.CitiesApiSevice
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.toObservable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SearchViewModel(app: Application) : AndroidViewModel(app) {

    private val TAG = "SearchViewModel"
    private val API_KEY = "6382139bc73c4a4a05d340bc54b53606"

    @Inject
    lateinit var citiesApiService: CitiesApiSevice

    val citiesLiveData: MutableLiveData<List<String>> = MutableLiveData()
    var gettingCities: Disposable? = null

    init {
        (app as App).appComponent.inject(this)
    }

    fun searchCity(cityName: String) {
        Log.d(TAG, "searching city $cityName")
        gettingCities = citiesApiService.getCitiesList(cityName, API_KEY)
            .flatMap { it.list.toObservable() }
            .map { "${it.name},${it.sys.country}" }
            .toList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                    Log.d(TAG, "list: $it")
                    citiesLiveData.value = it
                } , {

                }
            )
    }

    fun onPause() {
        gettingCities?.dispose()
    }
}