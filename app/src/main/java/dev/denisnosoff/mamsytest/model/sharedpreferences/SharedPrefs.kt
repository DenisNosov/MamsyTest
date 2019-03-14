package dev.denisnosoff.mamsytest.model.sharedpreferences

import android.content.Context
import android.content.SharedPreferences
import android.text.TextUtils
import dev.denisnosoff.mamsytest.R
import java.lang.NullPointerException
import java.util.*
import kotlin.collections.ArrayList

class SharedPrefs(context: Context) {

    private val PREFERENCES_NAME = "${context.getString(R.string.app_name)}.prefs"
    private val CITIES_LIST = "CITIES_LIST"

    private val preferences: SharedPreferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)

    var citiesList: ArrayList<String>?
        get() {
            val listString = preferences.getString(CITIES_LIST, "Moscow,RU,‗,London,GB,‗,Toronto,CA")
            return TextUtils.split(listString, ",‗,").toCollection(ArrayList())
        }
        set(value) {
            val stringArray = value?.toArray()
            preferences.edit().putString(CITIES_LIST, TextUtils.join(",‗,", stringArray)).apply()
        }
}