package com.silas.gpsworksapp.data.api

import com.silas.gpsworksapp.data.response.DetailResponse
import com.silas.gpsworksapp.data.response.OmdbResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface OmdbApi {
    @GET("/")
    suspend fun getListMovie(
        @Query("apikey") apikey: String = "ce220ed5",
        @Query("page") page: Int?,
        @Query("s") search: String?
    ): OmdbResponse

    @GET("/")
    suspend fun getDetailMovie(
        @Query("apikey") apikey: String = "ce220ed5",
        @Query("i") id: String?
    ): DetailResponse
}