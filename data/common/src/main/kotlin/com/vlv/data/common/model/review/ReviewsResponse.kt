package com.vlv.data.common.model.review


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.vlv.data.common.model.review.ReviewResponse

@JsonClass(generateAdapter = true)
data class ReviewsResponse(
    @Json(name = "id")
    val id: Int,
    @Json(name = "page")
    val page: Int,
    @Json(name = "results")
    val resultResponses: List<ReviewResponse>,
    @Json(name = "total_pages")
    val totalPages: Int,
    @Json(name = "total_results")
    val totalResults: Int
)