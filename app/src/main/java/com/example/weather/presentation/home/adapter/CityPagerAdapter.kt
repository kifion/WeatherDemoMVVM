package com.example.weather.presentation.home.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.weather.domain.model.CityDetail
import com.example.weather.presentation.home.CityViewPagerFragment

class CityPagerAdapter(
    fragmentManager: FragmentManager,
    private val cities: MutableList<CityDetail>
) :
    FragmentStatePagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        return CityViewPagerFragment.newInstance(cities[position])
    }

    override fun getCount(): Int {
        return cities.size
    }
}