package com.vlv.data.common.model.tvshow

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class TvShowsResponse(
    @Json(name = "total_pages")
    val totalPages: Int,
    @Json(name = "total_results")
    val totalResults: Int,
    val page: Int,
    @Json(name = "results")
    val tvShows: List<TvShowResponse>
)

@JsonClass(generateAdapter = true)
class TvShowResponse(
    val adult: Boolean = false,
    val id: Int,
    @Json(name = "backdrop_path")
    val backdropPath: String?,
    @Json(name = "poster_path")
    val posterPath: String?,
    @Json(name = "name")
    val title: String
)
