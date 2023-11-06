package com.vlv.movie.ui.detail.review

import androidx.lifecycle.ViewModel
import com.vlv.bondsmith.bondsmith
import com.vlv.common.data.review.Review
import com.vlv.data.common.model.review.ReviewsResponse
import com.vlv.movie.data.repository.MovieDetailRepository

class ReviewViewModel(private val repository: MovieDetailRepository) : ViewModel() {

    fun movieReviews(movieId: Int) = bondsmith<ReviewsResponse>()
        .request {
            repository.movieReviews(movieId)
        }
        .execute()
        .responseLiveData
        .map { it.resultResponses.map(::Review) }

}