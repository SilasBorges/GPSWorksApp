package com.silas.gpsworksapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingData
import com.silas.gpsworksapp.data.base.BaseViewModel
import com.silas.gpsworksapp.data.repository.GpsWorkRepository
import com.silas.gpsworksapp.data.response.Search
import com.silas.gpsworksapp.util.Resource
import com.silas.gpsworksapp.util.SingleLiveEvent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeViewModel(
    private var gpsWorkRepository: GpsWorkRepository,
    private val coroutinesDispatcher: CoroutineDispatcher
) : BaseViewModel() {

    val gpsWorkList = MutableLiveData<SingleLiveEvent<Resource<PagingData<Search>>>>()

    fun getMovie(searchString: String?) {
        gpsWorkList.postValue(SingleLiveEvent(Resource.loading()))

        viewModelScope.launch(coroutinesDispatcher) {
            try {
                val response = gpsWorkRepository.getMovie(searchString)
                response.flow.collectLatest {
                    gpsWorkList.postValue(SingleLiveEvent(Resource.success(it)))
                }
            } catch (e: Exception) {
                gpsWorkList.postValue(SingleLiveEvent(Resource.error()))
            }
        }
    }
}