package com.vlv.series.presentation.ui.detail.reviews

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vlv.bondsmith.bondsmith
import com.vlv.bondsmith.data.Response
import com.vlv.bondsmith.mapData
import com.vlv.common.data.review.Review
import com.vlv.data.common.model.review.ReviewResponse
import com.vlv.series.data.repository.SeriesDetailRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SeriesReviewViewModel(
    private val repository: SeriesDetailRepository
) : ViewModel() {

    private val _state = MutableStateFlow<Response<List<Review>>>(Response())

    val state: StateFlow<Response<List<Review>>>
        get() = _state.asStateFlow()

    fun seriesReview(seriesId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            bondsmith<List<ReviewResponse>>()
                .request {
                    repository.seriesReview(seriesId).resultResponses
                }
                .execute()
                .responseStateFlow
                .mapData {
                    it?.map(::Review)
                }
                .collectLatest {
                    _state.emit(it)
                }
        }
    }

}