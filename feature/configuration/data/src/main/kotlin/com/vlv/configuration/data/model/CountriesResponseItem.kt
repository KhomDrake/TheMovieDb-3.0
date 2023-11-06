package com.vlv.configuration.data.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CountriesResponseItem(
    @Json(name = "english_name")
    val englishName: String,
    @Json(name = "iso_3166_1")
    val iso31661: String,
    @Json(name = "native_name")
    val nativeName: String
)