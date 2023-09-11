package com.vlv.extensions


fun String.toUrlMovieDb() = kotlin.run {
    "https://image.tmdb.org/t/p/w500/$this"
}


