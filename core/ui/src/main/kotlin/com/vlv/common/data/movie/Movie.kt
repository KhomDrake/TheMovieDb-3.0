package com.vlv.common.data.movie

import android.os.Parcelable
import com.vlv.common.ui.DetailObject
import com.vlv.data.common.model.movie.MovieResponse
import com.vlv.data.common.model.people.MovieCreditResponse
import com.vlv.data.database.data.Favorite
import com.vlv.data.database.data.FavoriteType
import com.vlv.extensions.idInt
import kotlinx.parcelize.Parcelize

@Parcelize
class Movie(
    val adult: Boolean,
    val backdropPath: String?,
    val posterPath: String?,
    val apiId: Int,
    val title: String,
    val overview: String,
    val id: Int = idInt()
) : Parcelable {
    constructor(movieResponse: MovieResponse) : this(
        movieResponse.adult,
        movieResponse.backdropPath,
        movieResponse.posterPath,
        movieResponse.id,
        movieResponse.title,
        movieResponse.overview
    )

    constructor(favorite: Favorite) : this(
        false,
        favorite.backdrop,
        favorite.poster,
        favorite.itemId,
        favorite.name,
        favorite.overview
    )

    constructor(movieCreditResponse: MovieCreditResponse) : this(
        movieCreditResponse.adult,
        movieCreditResponse.backdropPath,
        movieCreditResponse.posterPath,
        movieCreditResponse.id,
        movieCreditResponse.title,
        movieCreditResponse.overview
    )
}

fun DetailObject.toMovie() = Movie(
    adult, backdropPath, posterPath, id, title, overview
)

fun Movie.toDetailObject() = DetailObject(
    apiId, posterPath, backdropPath, title, overview, adult
)

fun Movie.toFavorite() = Favorite(
    apiId,
    apiId,
    title,
    posterPath,
    backdropPath,
    overview,
    FavoriteType.MOVIE
)
