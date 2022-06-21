package com.silas.gpsworksapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import com.silas.gpsworksapp.data.base.BaseViewModel
import com.silas.gpsworksapp.data.repository.FavoriteRepository
import com.silas.gpsworksapp.data.response.Work
import com.silas.gpsworksapp.util.Resource
import com.silas.gpsworksapp.util.SingleLiveEvent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private var favoriteRepository: FavoriteRepository,
    private val coroutinesDispatcher: CoroutineDispatcher
) : BaseViewModel() {

    val favoriteResultLiveData = MutableLiveData<SingleLiveEvent<Resource<List<Work>>>>()

    init {
        getMovieFavorite()
    }

    fun getMovieFavorite() {
        viewModelScope.launch(coroutinesDispatcher) {
            favoriteResultLiveData.postValue(SingleLiveEvent(Resource.loading()))
            try {
                val response = favoriteRepository.favoriteAll()
                favoriteResultLiveData.postValue(SingleLiveEvent(Resource.success(response)))
            }catch (e: Exception){
                favoriteResultLiveData.postValue(SingleLiveEvent(Resource.error()))
            }
        }
    }
}