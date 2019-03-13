package dev.denisnosoff.mamsytest.mainactivity

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    var cities: MutableList<Fragment> = ArrayList()

    override fun getItem(position: Int): Fragment = cities[position]

    override fun getCount(): Int = cities.size

    fun addItem(fragment: Fragment) {
        cities.add(fragment)
        notifyDataSetChanged()
    }
}