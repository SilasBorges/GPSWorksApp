package com.silas.gpsworksapp.di

import com.silas.gpsworksapp.data.mapper.ConvertResponseToWorkMapper
import org.koin.dsl.module

val mapperModule = module {
    factory<ConvertResponseToWorkMapper> { ConvertResponseToWorkMapper.Impl() }
}