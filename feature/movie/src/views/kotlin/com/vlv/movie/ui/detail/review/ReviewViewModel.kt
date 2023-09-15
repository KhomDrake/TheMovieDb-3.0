package com.vlv.movie.ui.detail.review

import androidx.lifecycle.ViewModel
import com.vlv.bondsmith.bondsmith
import com.vlv.common.data.review.Review
import com.vlv.network.data.review.ReviewsResponse
import com.vlv.network.repository.MovieDetailRepository

class ReviewViewModel(private val repository: MovieDetailRepository) : ViewModel() {

    fun movieReviews(movieId: Int) = bondsmith<ReviewsResponse>()
        .request {
            repository.movieReviews(movieId)
        }
        .execute()
        .responseLiveData
        .map { it.resultResponses.map(::Review) }

}