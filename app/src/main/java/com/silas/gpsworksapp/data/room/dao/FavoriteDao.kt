package com.silas.gpsworksapp.data.room.dao

import androidx.room.*
import com.silas.gpsworksapp.data.response.Work

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM movie_detail")
    fun getAll(): List<Work>

    @Query("SELECT * FROM movie_detail WHERE imdbID == :imdbID")
    fun getMovie(imdbID: String): Work

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavorite(work: Work)

    @Query("DELETE FROM movie_detail WHERE imdbID = :imdbID")
    fun deleteFavorite(imdbID: String)
}