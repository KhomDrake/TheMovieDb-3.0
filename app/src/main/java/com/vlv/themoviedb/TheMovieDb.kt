package com.vlv.themoviedb

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import com.vlv.network.datastore.DataVault

open class TheMovieDb : Application() {

    override fun onCreate() {
        super.onCreate()
        DataVault.init(this)
        AndroidThreeTen.init(this)
    }
}