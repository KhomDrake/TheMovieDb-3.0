package com.vlv.extensions

import android.widget.ImageView
import coil.load


fun String.toUrlMovieDb() = kotlin.run {
    "https://image.tmdb.org/t/p/w500/$this"
}

fun String.loadUrl(view: ImageView) {
    view.load(this.toUrlMovieDb()) {
        crossfade(1000)
    }
}


