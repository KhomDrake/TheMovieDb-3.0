package com.vlv.data.network.model.credit


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class CreditsResponse(
    @Json(name = "cast")
    val castResponse: List<CastResponse>,
    @Json(name = "crew")
    val crewResponse: List<CrewResponse>,
    @Json(name = "id")
    val id: Int
)