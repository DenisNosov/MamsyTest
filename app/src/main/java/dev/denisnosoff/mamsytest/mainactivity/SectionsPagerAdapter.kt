package dev.denisnosoff.mamsytest.mainactivity

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.PagerAdapter

class SectionsPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    var cities: MutableList<Fragment> = ArrayList()

    override fun getItem(position: Int) = cities[position]

    override fun getCount() = cities.size

    override fun getItemPosition(`object`: Any) = PagerAdapter.POSITION_NONE

    fun addItem(fragment: Fragment) {
        cities.add(fragment)
        notifyDataSetChanged()
    }

    fun removeItem(position: Int) {
        cities.removeAt(position)
        notifyDataSetChanged()
    }
}