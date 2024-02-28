package com.vlv.movie.presentation.ui.detail.review

import androidx.lifecycle.ViewModel
import com.vlv.common.data.review.Review
import com.vlv.movie.data.repository.MovieDetailRepository

class ReviewViewModel(private val repository: MovieDetailRepository) : ViewModel() {

    fun movieReviews(movieId: Int) = repository
        .movieReviews(movieId)
        .responseLiveData
        .map { it.resultResponses.map(::Review) }

}