package com.vlv.themoviedb

import br.com.mrocigno.bigbrother.core.BigBrother

class DebugApplication : TheMovieDb() {

    override fun onCreate() {
        super.onCreate()
        BigBrother.watch(this, isBubbleEnabled = true)
    }

}