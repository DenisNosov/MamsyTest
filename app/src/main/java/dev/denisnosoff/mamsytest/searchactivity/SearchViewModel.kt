package dev.denisnosoff.mamsytest.searchactivity

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import dev.denisnosoff.mamsytest.App
import dev.denisnosoff.mamsytest.model.cities.CitiesApiSevice
import dev.denisnosoff.mamsytest.model.cities.CityItem
import dev.denisnosoff.mamsytest.util.CityToCityItem
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

    @Inject
    lateinit var converter: CityToCityItem

    val citiesLiveData: MutableLiveData<List<CityItem>> = MutableLiveData()
    private var gettingCities: Disposable? = null

    init {
        (app as App).appComponent.inject(this)
    }

    fun searchCity(cityName: String) {
        gettingCities = citiesApiService.getCitiesList(cityName, API_KEY)
            .flatMap { it.list.toObservable() }
            .map { "${it.name},‗,${it.sys.country},‗,${it.id}" }
            .map { converter.convertStringToItem(it) }
            .toList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                    citiesLiveData.value = it
                } , {
                    Log.d(TAG, it.localizedMessage)
                }
            )
    }

    fun onPause() {
        gettingCities?.dispose()
    }


}