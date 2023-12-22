package com.vlv.movie.presentation.ui.detail.review

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vlv.bondsmith.bondsmith
import com.vlv.bondsmith.data.Response
import com.vlv.bondsmith.mapData
import com.vlv.common.data.review.Review
import com.vlv.data.common.model.review.ReviewsResponse
import com.vlv.movie.data.repository.MovieDetailRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MovieReviewViewModel(
    private val repository: MovieDetailRepository
) : ViewModel() {

    private val _state = MutableStateFlow(Response<List<Review>>())

    val state: StateFlow<Response<List<Review>>>
        get() = _state.asStateFlow()

    fun movieReviews(movieId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            bondsmith<ReviewsResponse>()
                .request {
                    repository.movieReviews(movieId)
                }
                .execute()
                .stateFlow
                .mapData {
                    it?.resultResponses?.map(::Review) ?: listOf()
                }
                .collectLatest {
                    _state.emit(it)
                }
        }
    }

}