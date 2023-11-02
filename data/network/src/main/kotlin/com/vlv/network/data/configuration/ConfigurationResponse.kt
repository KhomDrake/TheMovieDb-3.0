package com.vlv.network.data.configuration


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ConfigurationResponse(
    @Json(name = "change_keys")
    val changeKeys: List<String>,
    @Json(name = "images")
    val images: ImagesConfigurationResponse
)

@JsonClass(generateAdapter = true)
data class ImagesConfigurationResponse(
    @Json(name = "backdrop_sizes")
    val backdropSizes: List<String>,
    @Json(name = "base_url")
    val baseUrl: String,
    @Json(name = "logo_sizes")
    val logoSizes: List<String>,
    @Json(name = "poster_sizes")
    val posterSizes: List<String>,
    @Json(name = "profile_sizes")
    val profileSizes: List<String>,
    @Json(name = "secure_base_url")
    val secureBaseUrl: String,
    @Json(name = "still_sizes")
    val stillSizes: List<String>
)