package dev.denisnosoff.mamsytest.util

import android.text.TextUtils
import dev.denisnosoff.mamsytest.model.cities.CityItem

class CityToCityItem {

    fun convertStringToItem(city: String) : CityItem {
        val array = TextUtils.split(city, ",‗,")
        return CityItem(array[0], array[1], array[2].toInt())
    }

    fun convertItemToString(item: CityItem) : String {
        val stringArray = arrayListOf(item.name, item.country, item.id.toString())
        return TextUtils.join(",‗,", stringArray)
    }

}