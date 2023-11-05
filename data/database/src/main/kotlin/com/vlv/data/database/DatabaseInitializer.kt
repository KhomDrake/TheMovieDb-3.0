package com.vlv.data.database

import androidx.room.Room
import androidx.startup.Initializer
import com.vlv.util.ModuleInitializer
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.Module
import org.koin.dsl.module

class DatabaseInitializer : ModuleInitializer() {

    override val modules: List<Module>
        get() = listOf(roomModule())

    private fun roomModule() = module {
        single {
            Room.databaseBuilder(
                androidApplication(),
                TheMovieDatabase::class.java,
                "Database TheMovieDb"
            ).build().dao()
        }
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf()
    }

}