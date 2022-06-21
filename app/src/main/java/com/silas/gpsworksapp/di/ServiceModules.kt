package com.silas.gpsworksapp.di

import com.silas.gpsworksapp.data.api.AuthInterceptor
import com.silas.gpsworksapp.util.BASE_URL
import org.koin.core.qualifier.named
import org.koin.dsl.module

val serviceModules = module {
    factory { AuthInterceptor() }
    factory { providesOkHttpClient() }
    single { provideRetrofit(get()) }
}