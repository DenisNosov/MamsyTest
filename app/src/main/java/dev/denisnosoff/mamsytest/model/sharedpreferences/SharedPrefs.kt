package dev.denisnosoff.mamsytest.model.sharedpreferences

import android.content.Context
import android.content.SharedPreferences
import dev.denisnosoff.mamsytest.R

class SharedPrefs(context: Context) {

    private val PREFERENCES_NAME = "${context.getString(R.string.app_name)}.prefs"
    private val CITIES_LIST = "CITIES_LIST"

    private val preferences: SharedPreferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)

    var citiesSet: MutableSet<String>?
        get() = preferences.getStringSet(CITIES_LIST, hashSetOf("Moscow,RU", "Los Angeles,US", "Toronto,CA"))
        set(value) = preferences.edit().putStringSet(CITIES_LIST, value).apply()
}