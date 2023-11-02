package com.vlv.network.data.review


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import org.threeten.bp.LocalDateTime

@JsonClass(generateAdapter = true)
data class ReviewResponse(
    @Json(name = "author")
    val author: String,
    @Json(name = "author_details")
    val responseAuthorDetails: ResponseAuthorDetails,
    @Json(name = "content")
    val content: String,
    @Json(name = "created_at")
    val createdAt: LocalDateTime,
    @Json(name = "id")
    val id: String,
    @Json(name = "updated_at")
    val updatedAt: String,
    @Json(name = "url")
    val url: String?
)