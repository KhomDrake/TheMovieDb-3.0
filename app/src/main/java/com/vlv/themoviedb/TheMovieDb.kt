package com.vlv.themoviedb

import android.app.Application
import androidx.startup.AppInitializer
import com.vlv.themoviedb.ui.MainInitializer
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class TheMovieDb : Application() {

    override fun onCreate() {
        super.onCreate()
        configKoin()
    }

    private fun configKoin() {
        startKoin {
            androidLogger()
            androidContext(this@TheMovieDb)
        }

        AppInitializer.getInstance(this)
            .initializeComponent(MainInitializer::class.java)
    }

}