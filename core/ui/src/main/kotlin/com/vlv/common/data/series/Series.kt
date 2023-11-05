package com.vlv.common.data.series

import android.os.Parcelable
import com.vlv.common.ui.DetailObject
import com.vlv.data.network.model.people.SeriesCreditResponse
import com.vlv.data.network.model.series.SeriesItemResponse
import com.vlv.data.network.database.data.Favorite
import com.vlv.data.network.database.data.FavoriteType
import kotlinx.parcelize.Parcelize

@Parcelize
class Series(
    val adult: Boolean,
    val id: Int,
    val backdropPath: String?,
    val posterPath: String?,
    val title: String
) : Parcelable {
    constructor(response: SeriesItemResponse): this(
        response.adult,
        response.id,
        response.backdropPath,
        response.posterPath,
        response.title,
    )

    constructor(favorite: Favorite): this(
        false,
        favorite.itemId,
        favorite.backdrop,
        favorite.poster,
        favorite.name
    )

    constructor(response: SeriesCreditResponse): this(
        response.adult,
        response.id,
        response.backdropPath,
        response.posterPath,
        response.name
    )
}

fun DetailObject.toSeries() = Series(
    adult, id, backdropPath, posterPath, title
)

fun Series.toDetailObject() = DetailObject(
    id, posterPath, backdropPath, title, overview = ""
)

fun Series.toFavorite() = Favorite(
    id,
    id,
    title,
    posterPath,
    backdropPath,
    "",
    FavoriteType.SERIES
)
