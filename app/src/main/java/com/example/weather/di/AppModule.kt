package com.example.weather.di

import android.content.Context
import com.example.weather.data.LocalStateDataHolder
import com.example.weather.data.SharedPreferencesUtil
import com.example.weather.data.network.mapper.CityDetailsMapper
import com.example.weather.data.network.mapper.CityListMapper
import com.example.weather.data.network.mapper.CityMapper
import com.example.weather.data.network.ApiService
import com.example.weather.data.network.NetworkService
import com.example.weather.data.repository.LocalRepository
import com.example.weather.data.repository.NetworkRepository
import com.example.weather.domain.repository.ILocalStateRepository
import com.example.weather.domain.repository.INetworkRepository
import com.example.weather.presentation.home.HomeViewModel
import com.example.weather.presentation.search.SearchViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {
    single { provideRetrofitService() }
    single { getSharedPreferences(androidApplication()) }
}

val viewModelModule = module {
    viewModel { HomeViewModel() }
    viewModel { SearchViewModel() }
}

val repositoryModule = module {
    single { provideLocationStateRepository() }
    single { provideNetworkRepository() }
}

val mapperModule = module {
    single { CityListMapper() }
    single { CityMapper() }
    single { CityDetailsMapper() }
}

fun provideRetrofitService(): ApiService = NetworkService.retrofitService()

fun getSharedPreferences(context: Context): SharedPreferencesUtil = SharedPreferencesUtil(context)

fun provideLocationStateRepository(): ILocalStateRepository =
    LocalRepository(LocalStateDataHolder())

fun provideNetworkRepository(): INetworkRepository = NetworkRepository()