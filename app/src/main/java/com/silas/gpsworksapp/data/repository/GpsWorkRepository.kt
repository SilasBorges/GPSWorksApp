package com.silas.gpsworksapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.silas.gpsworksapp.data.api.OmdbApi
import com.silas.gpsworksapp.data.datasource.GpsWorksDataSource
import com.silas.gpsworksapp.data.response.DetailResponse

class GpsWorkRepository(private var omdbApi: OmdbApi) {

    fun getMovie(searchString: String?) = Pager(
        config = PagingConfig(enablePlaceholders = false, pageSize = 25),
        pagingSourceFactory = {
            GpsWorksDataSource(omdbApi, searchString)
        }
    )

    suspend fun getDetailMovie(imdbID: String): DetailResponse = omdbApi.getDetailMovie(id = imdbID)

}