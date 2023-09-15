package com.vlv.network.data.movie

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.vlv.network.data.genre.GenreResponse

@JsonClass(generateAdapter = true)
class MovieDetailResponse(
    val id: Int,
    @Json(name = "imdb_id")
    val imdbId: String,
    val budget: Int,
    @Json(name = "homepage")
    val homePage: String,
    val revenue: Int,
    val runtime: Int,
    val genres: List<GenreResponse>,
    val video: Boolean,
    @Json(name = "vote_average")
    val voteAverage: Double,
    @Json(name = "spoken_languages")
    val spokenLanguages: List<Languages>,
    @Json(name = "original_title")
    val originalTitle: String,
    @Json(name = "original_language")
    val originalLanguage: String,
    @Json(name = "belongs_to_collection")
    val belongsToCollection: BelongsToCollection? = null,
    @Json(name = "production_companies")
    val productCompanies: List<ProductionCompany>,
    @Json(name = "production_countries")
    val productCountries: List<ProductionCountry>,
    val status: String,
    @Json(name = "release_date")
    val releaseData: String
)

@JsonClass(generateAdapter = true)
class BelongsToCollection(
    val id: Int,
    val name: String,
    @Json(name = "poster_path")
    val posterPath: String,
    @Json(name = "backdrop_path")
    val backdropPath: String
)

@JsonClass(generateAdapter = true)
class Languages(
    @Json(name = "english_name")
    val englishName: String,
    @Json(name = "iso_639_1")
    val iso639: String,
    @Json(name = "name")
    val name: String
)

@JsonClass(generateAdapter = true)
class ProductionCompany(
    val id: Int,
    @Json(name = "logo_path")
    val logoPath: String? = null,
    @Json(name = "name")
    val name: String,
    @Json(name = "origin_country")
    val originCountry: String
)

@JsonClass(generateAdapter = true)
class ProductionCountry(
    @Json(name = "iso_3166_1")
    val iso3166: String,
    @Json(name = "name")
    val name: String
)
