package com.silas.gpsworksapp.di

import org.koin.dsl.module

val apiModules = module {
    factory { provideOmdbApi(get()) }
}