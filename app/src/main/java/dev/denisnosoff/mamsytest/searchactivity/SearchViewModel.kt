package dev.denisnosoff.mamsytest.searchactivity

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import dev.denisnosoff.mamsytest.App
import dev.denisnosoff.mamsytest.model.cities.CitiesApiService
import dev.denisnosoff.mamsytest.model.cities.CityItem
import dev.denisnosoff.mamsytest.util.state.State
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.toObservable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SearchViewModel(app: Application) : AndroidViewModel(app) {

    @Inject
    lateinit var citiesApiService: CitiesApiService

    val citiesLiveData = MutableLiveData<List<CityItem>>()
    val stateLiveData = MutableLiveData<State>()
    val errorLiveData = MutableLiveData<String>()

    private var gettingCities: Disposable? = null

    init {
        (app as App).appComponent.inject(this)
        errorLiveData.value = "Unknown error"
        stateLiveData.value = State.SUCCESSFUL
    }

    fun searchCity(cityName: String) {
        gettingCities = citiesApiService.getCitiesList(cityName, App.API_KEY)
            .flatMap { it.list.toObservable() }
            .map { CityItem(it.name, it.sys.country, it.id) }
            .toList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                    citiesLiveData.value = it
                    stateLiveData.value = State.SUCCESSFUL
                } , {
                    errorLiveData.value = "Can't load cities"
                    stateLiveData.value = State.ERROR
                }
            )
    }

    fun onPause() {
        gettingCities?.dispose()
    }


}