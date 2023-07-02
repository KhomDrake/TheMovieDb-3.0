package com.vlv.movie.data

import com.vlv.network.data.movie.MovieResponse

class Movie(
    val adult: Boolean,
    val backdropPath: String?,
    val posterPath: String?,
    val id: Int,
    val title: String
) {
    constructor(movieResponse: MovieResponse) : this(
        movieResponse.adult,
        movieResponse.backdropPath,
        movieResponse.posterPath,
        movieResponse.id,
        movieResponse.title
    )
}