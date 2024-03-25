package com.vlv.themoviedb

import android.app.Application
import br.com.khomdrake.request.RequestHandler
import com.jakewharton.threetenabp.AndroidThreeTen
import com.vlv.data.local.datastore.DataVault


open class TheMovieDb : Application() {

    override fun onCreate() {
        super.onCreate()
        DataVault.init(this)
        RequestHandler.init(this, cleanCacheTimeout = false)
        AndroidThreeTen.init(this)
    }
}