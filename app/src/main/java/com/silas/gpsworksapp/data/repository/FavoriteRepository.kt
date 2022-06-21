package com.silas.gpsworksapp.data.repository

import com.silas.gpsworksapp.data.response.Work
import com.silas.gpsworksapp.data.room.dao.FavoriteDao

class FavoriteRepository(var favoriteDao: FavoriteDao) {

    suspend fun favoriteAll(): List<Work> {
        return favoriteDao.getAll()
    }

    suspend fun getMovie(imdbID: String): Work {
        return favoriteDao.getMovie(imdbID)
    }

    suspend fun insertFavorite(work: Work) {
        favoriteDao.insertFavorite(work)
    }

    suspend fun deleteFavorite(imdbID: String) {
        favoriteDao.deleteFavorite(imdbID)
    }
}