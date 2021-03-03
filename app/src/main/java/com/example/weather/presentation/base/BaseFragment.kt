package com.example.weather.presentation.base

import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {
    abstract fun getLayout(): Int
}