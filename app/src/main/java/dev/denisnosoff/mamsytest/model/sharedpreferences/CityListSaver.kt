package dev.denisnosoff.mamsytest.model.sharedpreferences

import android.app.Application
import android.content.Context
import dev.denisnosoff.mamsytest.App
import dev.denisnosoff.mamsytest.model.cities.CityItem
import dev.denisnosoff.mamsytest.util.CityToCityItem
import javax.inject.Inject

class CityListSaver(appContext: Application) {

    fun clear() {
        prefs.clear()
    }

    @Inject
    lateinit var converter: CityToCityItem

    private val prefs: SharedPrefs = SharedPrefs(appContext)

    init {
        (appContext as App).appComponent.inject(this)
    }

    var citiesList: ArrayList<CityItem>
        get() {
            return ArrayList(prefs.citiesList!!.map {
                converter.convertStringToItem(it)
            })
        }
        set(value) {
            prefs.citiesList = ArrayList(value.map {
                converter.convertItemToString(it)
            })
        }

}