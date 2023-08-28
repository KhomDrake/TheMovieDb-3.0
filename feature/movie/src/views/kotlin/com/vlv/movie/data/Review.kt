package com.vlv.movie.data

import com.vlv.network.data.review.ReviewResponse

class Review(
    val id: String,
    val author: String,
    val content: String,
    val createdAt: String,
    val url: String?
) {
    constructor(review: ReviewResponse) : this(
        review.id,
        review.author,
        review.content,
        review.createdAt,
        review.url
    )
}