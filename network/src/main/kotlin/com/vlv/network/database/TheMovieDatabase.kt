package com.vlv.network.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vlv.network.database.data.CountryEntity
import com.vlv.network.database.data.Favorite
import com.vlv.network.database.data.History
import com.vlv.network.database.data.ImageEntity
import com.vlv.network.database.data.LanguageEntity

@Database(
    entities = [
        History::class,
        Favorite::class,
        CountryEntity::class,
        ImageEntity::class,
        LanguageEntity::class
    ],
    version = 1
)
abstract class TheMovieDatabase : RoomDatabase() {

    abstract fun dao(): TheMovieDbDao

}