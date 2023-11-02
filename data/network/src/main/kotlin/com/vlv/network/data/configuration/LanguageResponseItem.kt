package com.vlv.network.data.configuration


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LanguageResponseItem(
    @Json(name = "english_name")
    val englishName: String,
    @Json(name = "name")
    val iso6391: String,
    @Json(name = "iso_639_1")
    val name: String
)