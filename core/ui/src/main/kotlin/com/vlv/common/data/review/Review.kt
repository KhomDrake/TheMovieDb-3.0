package com.vlv.common.data.review

import com.vlv.data.common.model.review.ReviewResponse
import com.vlv.extensions.patternFullDate
import com.vlv.extensions.toFormattedString

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
        review.createdAt.toFormattedString(patternFullDate()),
        review.url
    )
}