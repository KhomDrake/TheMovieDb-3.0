package com.vlv.data.network.model.series

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class SeriesResponse(
    @Json(name = "total_pages")
    val totalPages: Int,
    @Json(name = "total_results")
    val totalResults: Int,
    val page: Int,
    @Json(name = "results")
    val series: List<SeriesItemResponse>
)

@JsonClass(generateAdapter = true)
class SeriesItemResponse(
    val adult: Boolean = false,
    val id: Int,
    @Json(name = "backdrop_path")
    val backdropPath: String?,
    @Json(name = "poster_path")
    val posterPath: String?,
    @Json(name = "name")
    val title: String
)
