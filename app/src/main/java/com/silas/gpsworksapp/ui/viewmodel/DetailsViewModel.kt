package com.silas.gpsworksapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import com.silas.gpsworksapp.data.base.BaseViewModel
import com.silas.gpsworksapp.data.mapper.ConvertResponseToWorkMapper
import com.silas.gpsworksapp.data.repository.FavoriteRepository
import com.silas.gpsworksapp.data.repository.GpsWorkRepository
import com.silas.gpsworksapp.data.response.DetailResponse
import com.silas.gpsworksapp.data.response.Work
import com.silas.gpsworksapp.util.Resource
import com.silas.gpsworksapp.util.SingleLiveEvent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

class DetailsViewModel(
    private var gpsWorkRepository: GpsWorkRepository,
    private var favoriteRepository: FavoriteRepository,
    private var convertResponseToWorkMapper: ConvertResponseToWorkMapper,
    private val coroutinesDispatcher: CoroutineDispatcher
) : BaseViewModel() {

    val detailResultLiveData = MutableLiveData<SingleLiveEvent<Resource<DetailResponse>>>()
    val favoriteResultLiveData = MutableLiveData<SingleLiveEvent<Resource<Work>>>()
    val insertMovieFavorite = MutableLiveData<SingleLiveEvent<Resource<Unit>>>()
    val removeMovieFavorite = MutableLiveData<SingleLiveEvent<Resource<Unit>>>()

    fun setupInit(imdbID: String){
        getMovieFavorite(imdbID)
        getMovieDetail(imdbID)
    }

    fun insertMovieFavorite(detailResponse: DetailResponse) {
        viewModelScope.launch(coroutinesDispatcher) {
            insertMovieFavorite.postValue(SingleLiveEvent(Resource.loading()))
            try {
                favoriteRepository.insertFavorite(convertResponseToWorkMapper.convertResponse(detailResponse)).apply {
                    getMovieFavorite(detailResponse.imdbID)
                    insertMovieFavorite.postValue(SingleLiveEvent(Resource.success(this)))
                }
            }catch (e: Exception){
                insertMovieFavorite.postValue(SingleLiveEvent(Resource.error()))
            }
        }
    }

    fun removeMovieFavorite(imdbID: String) {
        viewModelScope.launch(coroutinesDispatcher) {
            removeMovieFavorite.postValue(SingleLiveEvent(Resource.loading()))
            try {
                favoriteRepository.deleteFavorite(imdbID).apply {
                    removeMovieFavorite.postValue(SingleLiveEvent(Resource.success(this)))
                }
            }catch (e: Exception){
                removeMovieFavorite.postValue(SingleLiveEvent(Resource.error()))
            }
        }
    }

    private fun getMovieFavorite(imdbID: String) {
        viewModelScope.launch(coroutinesDispatcher) {
            val response = favoriteRepository.getMovie(imdbID)
            favoriteResultLiveData.postValue(SingleLiveEvent(Resource.success(response)))
        }
    }

    private fun getMovieDetail(imdbID: String) {
        viewModelScope.launch(coroutinesDispatcher) {
            detailResultLiveData.postValue(SingleLiveEvent(Resource.loading()))
            try {
                val response = gpsWorkRepository.getDetailMovie(imdbID)
                detailResultLiveData.postValue(SingleLiveEvent(Resource.success(response)))
            }catch (e: Exception){
                detailResultLiveData.postValue(SingleLiveEvent(Resource.error()))
            }
        }
    }
}