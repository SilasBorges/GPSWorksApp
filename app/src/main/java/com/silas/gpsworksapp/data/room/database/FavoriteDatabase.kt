package com.silas.gpsworksapp.data.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.silas.gpsworksapp.data.room.dao.FavoriteDao
import com.silas.gpsworksapp.data.response.Work

@Database(entities = [Work::class], version = 1, exportSchema = false)
abstract class FavoriteDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
}