package dev.denisnosoff.mamsytest.util

import android.text.TextUtils
import dev.denisnosoff.mamsytest.model.cities.CityItem

class CityToCityItem {

    fun convert(city: String) : CityItem {
        val array = TextUtils.split(city, ",‗,")
        return CityItem(array[0], array[1].toInt())
    }

}