package com.silas.gpsworksapp.di

import com.silas.gpsworksapp.ui.viewmodel.DetailsViewModel
import com.silas.gpsworksapp.ui.viewmodel.FavoriteViewModel
import com.silas.gpsworksapp.ui.viewmodel.HomeViewModel
import org.koin.dsl.module

val viewModelModules = module {

    factory { HomeViewModel(get(), get()) }
    factory { DetailsViewModel(get(), get(), get(), get()) }
    factory { FavoriteViewModel(get(), get()) }

}