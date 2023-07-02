package com.vlv.series.data

import com.vlv.network.data.series.SeriesItemResponse

class Series(
    val adult: Boolean,
    val id: Int,
    val backdropPath: String?,
    val posterPath: String?,
    val title: String
) {
    constructor(response: SeriesItemResponse): this(
        response.adult,
        response.id,
        response.backdropPath,
        response.posterPath,
        response.title,
    )
}