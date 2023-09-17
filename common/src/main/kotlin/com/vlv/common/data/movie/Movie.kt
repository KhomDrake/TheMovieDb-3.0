package com.vlv.common.data.movie

import android.os.Parcelable
import com.vlv.common.ui.DetailObject
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

fun DetailObject.toMovie() = Movie(
    adult, backdropPath, posterPath, id, title, overview
)

fun Movie.toDetailObject() = DetailObject(
    id, posterPath, backdropPath, title, overview, adult
)
