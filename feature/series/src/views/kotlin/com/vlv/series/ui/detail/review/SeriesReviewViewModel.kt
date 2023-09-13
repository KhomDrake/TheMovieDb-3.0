package com.vlv.series.ui.detail.review

import androidx.lifecycle.ViewModel
import com.vlv.bondsmith.bondsmith
import com.vlv.common.data.review.Review
import com.vlv.network.data.review.ReviewsResponse
import com.vlv.network.repository.SeriesDetailRepository

class SeriesReviewViewModel(private val repository: SeriesDetailRepository) : ViewModel() {

    fun reviews(seriesId: Int) = bondsmith<ReviewsResponse>()
        .request {
            repository.seriesReview(seriesId)
        }
        .execute()
        .responseLiveData
        .map {
            it.resultResponses.map(::Review)
        }

}