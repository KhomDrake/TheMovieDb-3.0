package com.vlv.tv_show.ui.detail.review

import androidx.lifecycle.ViewModel
import com.vlv.common.data.review.Review
import com.vlv.tv_show.data.repository.TvShowDetailRepository

class SeriesReviewViewModel(private val repository: TvShowDetailRepository) : ViewModel() {

    fun reviews(seriesId: Int) = repository
        .tvShowReview(seriesId)
        .responseLiveData
        .map {
            it.resultResponses.map(::Review)
        }

}