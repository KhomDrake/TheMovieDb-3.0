package com.vlv.common.data.series

import android.os.Parcelable
import com.vlv.common.ui.DetailObject
import com.vlv.data.common.model.people.SeriesCreditResponse
import com.vlv.data.common.model.series.SeriesItemResponse
import com.vlv.data.database.data.Favorite
import com.vlv.data.database.data.FavoriteType
import com.vlv.extensions.idInt
import kotlinx.parcelize.Parcelize

@Parcelize
class Series(
    val adult: Boolean,
    val apiId: Int,
    val backdropPath: String?,
    val posterPath: String?,
    val title: String,
    val id: Int = idInt()
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
    apiId, posterPath, backdropPath, title, overview = ""
)

fun Series.toFavorite() = Favorite(
    apiId,
    apiId,
    title,
    posterPath,
    backdropPath,
    "",
    FavoriteType.SERIES
)
