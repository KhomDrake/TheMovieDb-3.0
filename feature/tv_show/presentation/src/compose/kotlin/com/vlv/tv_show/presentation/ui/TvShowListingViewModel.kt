package com.vlv.tv_show.presentation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.vlv.common.data.tv_show.TvShow
import com.vlv.common.data.tv_show.TvShowListType
import com.vlv.data.common.model.TimeWindow
import com.vlv.tv_show.data.repository.TvShowRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class TvShowListingViewModel(
    private val repository: TvShowRepository
) : ViewModel() {

    private val _state: MutableStateFlow<PagingData<TvShow>> =
        MutableStateFlow(PagingData.empty())

    val state: Flow<PagingData<TvShow>>
        get() = _state

    private val pagingConfig = PagingConfig(
        pageSize = 20,
        prefetchDistance = 5,
        maxSize = 60,
        initialLoadSize = 20
    )

    fun series(tvShowListType: TvShowListType) {
        viewModelScope.launch(Dispatchers.IO) {
            tvShowsByListType(tvShowListType)
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

    private suspend fun tvShowsByListType(tvShowListType: TvShowListType) =
        when(tvShowListType) {
            TvShowListType.TRENDING -> repository.trendingNowPaging(pagingConfig, TimeWindow.WEEK)
            TvShowListType.ON_THE_AIR -> repository.onTheAirPaging(pagingConfig)
            TvShowListType.POPULAR -> repository.popularPaging(pagingConfig)
            TvShowListType.TOP_RATED -> repository.topRatedPaging(pagingConfig)
            TvShowListType.AIRING_TODAY -> repository.airingTodayPaging(pagingConfig)
        }

}