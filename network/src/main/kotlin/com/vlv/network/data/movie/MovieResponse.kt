package com.vlv.network.data.movie

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class MoviesResponse(
    @Json(name = "total_pages")
    val totalPages: Int,
    @Json(name = "total_results")
    val totalResults: Int,
    val page: Int,
    @Json(name = "results")
    val movies: List<MovieResponse>
)

@JsonClass(generateAdapter = true)
class MovieResponse(
    val adult: Boolean,
    @Json(name = "backdrop_path")
    val backdropPath: String?,
    @Json(name = "poster_path")
    val posterPath: String?,
    val id: Int,
    val title: String
)