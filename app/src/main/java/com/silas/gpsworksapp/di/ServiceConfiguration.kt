package com.silas.gpsworksapp.di

import com.silas.gpsworksapp.data.api.OmdbApi
import com.silas.gpsworksapp.util.*
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

fun providesOkHttpClient(): OkHttpClient {
    val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
        .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
        .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
        .addInterceptor(cacheInterceptor)
    if (BuildConfig.DEBUG) okHttpClient.addInterceptor(loggingInterceptor)
    return okHttpClient.build()
}

fun provideOmdbApi(retrofit: Retrofit): OmdbApi = retrofit.create(OmdbApi::class.java)

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =  Retrofit.Builder()
    .baseUrl(BASE_URL)
    .client(okHttpClient)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

private val cacheInterceptor = Interceptor { chain ->
    val response: Response = chain.proceed(chain.request())
    val cacheControl = CacheControl.Builder()
        .maxAge(MAX_AGE, TimeUnit.DAYS)
        .build()
    response.newBuilder()
        .header("Cache-Control", cacheControl.toString())
        .build()
}

private val loggingInterceptor =
    HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }