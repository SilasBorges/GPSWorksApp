package com.silas.gpsworksapp.data.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.silas.gpsworksapp.data.api.OmdbApi
import com.silas.gpsworksapp.data.response.Search
import com.silas.gpsworksapp.util.STARTING_OFFSET_INDEX
import java.io.IOException

class GpsWorksDataSource(private val omdbApi: OmdbApi, private val searchString: String?) :
    PagingSource<Int, Search>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Search> {
        val pageIndex  = params.key ?: STARTING_OFFSET_INDEX
        return try {
            val data = omdbApi.getListMovie(search = searchString, page = pageIndex)
            val filteredData = data.search
            LoadResult.Page(
                data = filteredData,
                prevKey = if (pageIndex == STARTING_OFFSET_INDEX) null else pageIndex - 1,
                nextKey = pageIndex + 1
            )
        } catch (throwable: Throwable) {
            var exception = throwable
            if (exception is IOException) {
                exception = IOException("Please check internet connection")
            }
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Search>): Int? {
        return state.anchorPosition
    }
}