package com.vlv.network.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vlv.network.database.data.Favorite
import com.vlv.network.database.data.History

@Database(entities = [History::class, Favorite::class], version = 1)
abstract class TheMovieDatabase : RoomDatabase() {

    abstract fun dao(): TheMovieDbDao

}