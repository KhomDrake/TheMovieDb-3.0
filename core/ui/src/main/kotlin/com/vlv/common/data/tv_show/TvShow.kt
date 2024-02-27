package com.vlv.common.data.tv_show

import android.os.Parcelable
import com.vlv.common.ui.DetailObject
import com.vlv.data.common.model.people.TvShowCreditResponse
import com.vlv.data.common.model.tvshow.TvShowResponse
import com.vlv.data.database.data.Favorite
import com.vlv.data.database.data.ItemType
import com.vlv.extensions.idInt
import kotlinx.parcelize.Parcelize

@Parcelize
class TvShow(
    val adult: Boolean,
    val apiId: Int,
    val backdropPath: String?,
    val posterPath: String?,
    val title: String,
    val id: Int = idInt()
) : Parcelable {
    constructor(response: TvShowResponse): this(
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

    constructor(response: TvShowCreditResponse): this(
        response.adult,
        response.id,
        response.backdropPath,
        response.posterPath,
        response.name
    )
}

fun DetailObject.toSeries() = TvShow(
    adult, id, backdropPath, posterPath, title
)

fun TvShow.toDetailObject() = DetailObject(
    apiId, posterPath, backdropPath, title, overview = ""
)

fun TvShow.toFavorite() = Favorite(
    apiId,
    apiId,
    title,
    posterPath,
    backdropPath,
    "",
    ItemType.TV_SHOW
)
