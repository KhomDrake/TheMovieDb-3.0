package com.vlv.movie.presentation.ui.detail.review

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vlv.bondsmith.data.flow.MutableResponseStateFlow
import com.vlv.bondsmith.data.flow.ResponseStateFlow
import com.vlv.bondsmith.data.flow.asResponseStateFlow
import com.vlv.common.data.review.Review
import com.vlv.movie.data.repository.MovieDetailRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MovieReviewViewModel(
    private val repository: MovieDetailRepository
) : ViewModel() {

    private val _state = MutableResponseStateFlow<List<Review>>()

    val state: ResponseStateFlow<List<Review>>
        get() = _state.asResponseStateFlow()

    fun movieReviews(movieId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.movieReviews(movieId)
                .responseStateFlow
                .mapData {
                    it?.resultResponses?.map(::Review) ?: listOf()
                }
                .collectLatest {
                    _state.emit(it)
                }
        }
    }

}