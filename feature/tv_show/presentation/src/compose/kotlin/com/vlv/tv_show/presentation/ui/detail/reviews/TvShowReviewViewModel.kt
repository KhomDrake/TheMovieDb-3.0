package com.vlv.tv_show.presentation.ui.detail.reviews

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vlv.bondsmith.data.Response
import com.vlv.common.data.review.Review
import com.vlv.tv_show.data.repository.TvShowDetailRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class TvShowReviewViewModel(
    private val repository: TvShowDetailRepository
) : ViewModel() {

    private val _state = MutableStateFlow<Response<List<Review>>>(Response())

    val state: StateFlow<Response<List<Review>>>
        get() = _state.asStateFlow()

    fun tvShowReview(seriesId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.tvShowReview(seriesId)
                .responseStateFlow
                .mapData {
                    it?.resultResponses?.map(::Review)
                }
                .collectLatest {
                    _state.emit(it)
                }
        }
    }

}