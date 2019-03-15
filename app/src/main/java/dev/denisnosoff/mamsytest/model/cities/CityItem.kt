package dev.denisnosoff.mamsytest.model.cities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CityItem(
    val name: String,
    val country: String,
    val id: Int
) : Parcelable