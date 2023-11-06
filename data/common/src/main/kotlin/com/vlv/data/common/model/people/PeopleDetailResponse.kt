package com.vlv.data.common.model.people


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import org.threeten.bp.LocalDate

@JsonClass(generateAdapter = true)
data class PeopleDetailResponse(
    @Json(name = "adult")
    val adult: Boolean,
    @Json(name = "also_known_as")
    val alsoKnownAs: List<String>,
    @Json(name = "biography")
    val biography: String,
    @Json(name = "birthday")
    val birthday: LocalDate?,
    @Json(name = "deathday")
    val deathday: Any?,
    @Json(name = "gender")
    val gender: Int,
    @Json(name = "homepage")
    val homepage: Any?,
    @Json(name = "id")
    val id: Int,
    @Json(name = "imdb_id")
    val imdbId: String,
    @Json(name = "known_for_department")
    val knownForDepartment: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "place_of_birth")
    val placeOfBirth: String?,
    @Json(name = "popularity")
    val popularity: Double,
    @Json(name = "profile_path")
    val profilePath: String?
)