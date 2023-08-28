package com.vlv.movie.data

import android.os.Parcelable
import com.vlv.network.data.movie.MovieResponse
import kotlinx.parcelize.Parcelize

@Parcelize
class Movie(
    val adult: Boolean,
    val backdropPath: String?,
    val posterPath: String?,
    val id: Int,
    val title: String,
    val overview: String
) : Parcelable {
    constructor(movieResponse: MovieResponse) : this(
        movieResponse.adult,
        movieResponse.backdropPath,
        movieResponse.posterPath,
        movieResponse.id,
        movieResponse.title,
        movieResponse.overview
    )
}