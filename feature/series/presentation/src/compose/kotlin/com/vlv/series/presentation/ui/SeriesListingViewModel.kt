package com.vlv.series.presentation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.vlv.common.data.movie.MovieListType
import com.vlv.common.data.series.Series
import com.vlv.common.data.series.SeriesListType
import com.vlv.data.common.model.TimeWindow
import com.vlv.series.data.repository.SeriesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class SeriesListingViewModel(
    private val repository: SeriesRepository
) : ViewModel() {

    private val _state: MutableStateFlow<PagingData<Series>> =
        MutableStateFlow(PagingData.empty())

    val state: Flow<PagingData<Series>>
        get() = _state.asStateFlow()

    private val pagingConfig = PagingConfig(
        pageSize = 20,
        prefetchDistance = 5,
        maxSize = 60,
        initialLoadSize = 20
    )

    fun series(seriesListType: SeriesListType) {
        viewModelScope.launch(Dispatchers.IO) {
            seriesByListType(seriesListType)
                .map {
                    it.map(::Series)
                }
                .cachedIn(viewModelScope)
                .distinctUntilChanged()
                .collectLatest {
                    _state.emit(it)
                }
        }
    }

    private suspend fun seriesByListType(seriesListType: SeriesListType) =
        when(seriesListType) {
            SeriesListType.TRENDING -> repository.trendingNowPaging(pagingConfig, TimeWindow.WEEK)
            SeriesListType.ON_THE_AIR -> repository.onTheAirPaging(pagingConfig)
            SeriesListType.POPULAR -> repository.popularPaging(pagingConfig)
            SeriesListType.TOP_RATED -> repository.topRatedPaging(pagingConfig)
            SeriesListType.AIRING_TODAY -> repository.airingTodayPaging(pagingConfig)
        }

}