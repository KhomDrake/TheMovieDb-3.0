package com.vlv.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vlv.data.database.data.CountryEntity
import com.vlv.data.database.data.Favorite
import com.vlv.data.database.data.History
import com.vlv.data.database.data.ImageEntity
import com.vlv.data.database.data.LanguageEntity

@Database(
    entities = [
        History::class,
        Favorite::class,
        CountryEntity::class,
        ImageEntity::class,
        LanguageEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class TheMovieDatabase : RoomDatabase() {

    abstract fun dao(): TheMovieDbDao

}