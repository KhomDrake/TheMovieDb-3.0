package com.vlv.series.ui.listing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.map
import com.vlv.common.data.series.Series
import com.vlv.common.data.series.SeriesListType
import com.vlv.network.repository.SeriesRepository
import com.vlv.network.repository.TimeWindow
import kotlinx.coroutines.flow.map

class ListingSeriesViewModel(private val repository: SeriesRepository) : ViewModel() {

    private val pagingConfig = PagingConfig(
        pageSize = 20,
        prefetchDistance = 5,
        maxSize = 60,
        initialLoadSize = 20
    )

    fun loadingType(type: SeriesListType) =
        loadByType(type).map {
            it.map { item ->
                Series(item)
            }
        }
        .cachedIn(viewModelScope)

    private fun loadByType(type: SeriesListType) = when(type) {
        SeriesListType.TRENDING -> repository.trendingNowPaging(pagingConfig, TimeWindow.DAY)
        SeriesListType.AIRING_TODAY -> repository.airingTodayPaging(pagingConfig)
        SeriesListType.ON_THE_AIR -> repository.onTheAirPaging(pagingConfig)
        SeriesListType.TOP_RATED -> repository.topRatedPaging(pagingConfig)
        SeriesListType.POPULAR -> repository.popularPaging(pagingConfig)
    }


}