package com.vlv.themoviedb

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen

open class TheMovieDb : Application() {

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this);
    }
}