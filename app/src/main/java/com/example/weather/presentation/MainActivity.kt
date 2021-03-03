package com.example.weather.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.example.weather.R
import com.example.weather.presentation.home.CityViewPagerFragment

class MainActivity : AppCompatActivity(), CityViewPagerFragment.Callback {
    private val navController by lazy {
        (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment).navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun setCityPagerCurrentPage(page: Int) {
        // Not yet implemented
    }
}