package com.vlv.tv_show.presentation.ui.detail.recommendations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.vlv.common.data.series.TvShow
import com.vlv.tv_show.data.repository.TvShowDetailRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class TvShowsRecommendationViewModel(
    private val repository: TvShowDetailRepository
) : ViewModel() {

    private val _state: MutableStateFlow<PagingData<TvShow>> =
        MutableStateFlow(PagingData.empty())

    val state: Flow<PagingData<TvShow>>
        get() = _state.asStateFlow()

    private val pagingConfig = PagingConfig(
        pageSize = 20,
        prefetchDistance = 5,
        maxSize = 60,
        initialLoadSize = 20
    )

    fun tvShowRecommendation(tvShowId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.tvShowRecommendation(tvShowId, pagingConfig)
                .map {
                    it.map(::TvShow)
                }
                .cachedIn(viewModelScope)
                .distinctUntilChanged()
                .collectLatest {
                    _state.emit(it)
                }
        }
    }

}