package com.vlv.common.data.series

import android.os.Parcelable
import com.vlv.common.ui.DetailObject
import com.vlv.network.data.series.SeriesItemResponse
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
}

fun DetailObject.toSeries() = Series(
    adult, id, backdropPath, posterPath, title
)

fun Series.toDetailObject() = DetailObject(
    id, posterPath, backdropPath, title, overview = ""
)