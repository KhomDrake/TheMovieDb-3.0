package com.vlv.data.common.model.tvshow


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.vlv.data.common.model.genre.GenreResponse
import org.threeten.bp.LocalDate

@JsonClass(generateAdapter = true)
data class TvShowDetailResponse(
    @Json(name = "adult")
    val adult: Boolean,
    @Json(name = "backdrop_path")
    val backdropPath: String?,
    @Json(name = "created_by")
    val creators: List<CreatorResponse>,
    @Json(name = "episode_run_time")
    val episodeRunTime: List<Int>,
    @Json(name = "first_air_date")
    val firstAirDate: LocalDate,
    @Json(name = "genres")
    val genres: List<GenreResponse>,
    @Json(name = "homepage")
    val homepage: String,
    @Json(name = "id")
    val id: Int,
    @Json(name = "in_production")
    val inProduction: Boolean,
    @Json(name = "languages")
    val languages: List<String>,
    @Json(name = "last_air_date")
    val lastAirDate: LocalDate,
    @Json(name = "last_episode_to_air")
    val lastEpisodeToAir: LastEpisodeToAir?,
    @Json(name = "name")
    val name: String,
    @Json(name = "networks")
    val networks: List<Network>,
    @Json(name = "next_episode_to_air")
    val nextEpisodeToAir: NextEpisodeToAir?,
    @Json(name = "number_of_episodes")
    val numberOfEpisodes: Int,
    @Json(name = "number_of_seasons")
    val numberOfSeasons: Int,
    @Json(name = "origin_country")
    val originCountry: List<String>,
    @Json(name = "original_language")
    val originalLanguage: String,
    @Json(name = "original_name")
    val originalName: String,
    @Json(name = "overview")
    val overview: String,
    @Json(name = "popularity")
    val popularity: Double,
    @Json(name = "poster_path")
    val posterPath: String?,
    @Json(name = "production_companies")
    val productionCompanies: List<ProductionCompany>,
    @Json(name = "production_countries")
    val productionCountries: List<ProductionCountry>,
    @Json(name = "seasons")
    val seasons: List<SeasonResponse>,
    @Json(name = "spoken_languages")
    val spokenLanguages: List<SpokenLanguage>,
    @Json(name = "status")
    val status: String,
    @Json(name = "tagline")
    val tagline: String,
    @Json(name = "type")
    val type: String,
    @Json(name = "vote_average")
    val voteAverage: Double,
    @Json(name = "vote_count")
    val voteCount: Int
)

@JsonClass(generateAdapter = true)
data class CreatorResponse(
    @Json(name = "credit_id")
    val creditId: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "profile_path")
    val profilePath: String?,
    @Json(name = "gender")
    val gender: Int,
    @Json(name = "id")
    val id: Int
)



@JsonClass(generateAdapter = true)
data class LastEpisodeToAir(
    @Json(name = "air_date")
    val airDate: LocalDate,
    @Json(name = "episode_number")
    val episodeNumber: Int,
    @Json(name = "episode_type")
    val episodeType: String,
    @Json(name = "id")
    val id: Int,
    @Json(name = "name")
    val name: String,
    @Json(name = "overview")
    val overview: String,
    @Json(name = "production_code")
    val productionCode: String,
    @Json(name = "runtime")
    val runtime: Int?,
    @Json(name = "season_number")
    val seasonNumber: Int,
    @Json(name = "show_id")
    val showId: Int,
    @Json(name = "still_path")
    val stillPath: String?,
    @Json(name = "vote_average")
    val voteAverage: Double,
    @Json(name = "vote_count")
    val voteCount: Int
)

@JsonClass(generateAdapter = true)
data class Network(
    @Json(name = "id")
    val id: Int,
    @Json(name = "logo_path")
    val logoPath: String?,
    @Json(name = "name")
    val name: String,
    @Json(name = "origin_country")
    val originCountry: String
)

@JsonClass(generateAdapter = true)
data class NextEpisodeToAir(
    @Json(name = "air_date")
    val airDate: LocalDate,
    @Json(name = "episode_number")
    val episodeNumber: Int,
    @Json(name = "episode_type")
    val episodeType: String,
    @Json(name = "id")
    val id: Int,
    @Json(name = "name")
    val name: String,
    @Json(name = "overview")
    val overview: String,
    @Json(name = "production_code")
    val productionCode: String,
    @Json(name = "runtime")
    val runtime: Int?,
    @Json(name = "season_number")
    val seasonNumber: Int,
    @Json(name = "show_id")
    val showId: Int,
    @Json(name = "still_path")
    val stillPath: String?,
    @Json(name = "vote_average")
    val voteAverage: Double,
    @Json(name = "vote_count")
    val voteCount: Int
)

@JsonClass(generateAdapter = true)
data class ProductionCompany(
    @Json(name = "id")
    val id: Int,
    @Json(name = "logo_path")
    val logoPath: String?,
    @Json(name = "name")
    val name: String,
    @Json(name = "origin_country")
    val originCountry: String
)

@JsonClass(generateAdapter = true)
data class ProductionCountry(
    @Json(name = "iso_3166_1")
    val iso31661: String,
    @Json(name = "name")
    val name: String
)

@JsonClass(generateAdapter = true)
data class SeasonResponse(
    @Json(name = "air_date")
    val airDate: LocalDate?,
    @Json(name = "episode_count")
    val episodeCount: Int,
    @Json(name = "id")
    val id: Int,
    @Json(name = "name")
    val name: String,
    @Json(name = "overview")
    val overview: String,
    @Json(name = "poster_path")
    val posterPath: String?,
    @Json(name = "season_number")
    val seasonNumber: Int,
    @Json(name = "vote_average")
    val voteAverage: Double
)

@JsonClass(generateAdapter = true)
data class SpokenLanguage(
    @Json(name = "english_name")
    val englishName: String,
    @Json(name = "iso_639_1")
    val iso6391: String,
    @Json(name = "name")
    val name: String
)
