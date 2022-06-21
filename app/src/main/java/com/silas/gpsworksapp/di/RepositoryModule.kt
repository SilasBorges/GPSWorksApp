package com.silas.gpsworksapp.di

import com.silas.gpsworksapp.data.repository.FavoriteRepository
import com.silas.gpsworksapp.data.repository.GpsWorkRepository
import org.koin.dsl.module

val repositoryModule = module {

    factory { GpsWorkRepository(get()) }
    factory { FavoriteRepository(get()) }

}