package com.vlv.tv_show.ui.listing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.map
import com.vlv.common.data.tv_show.TvShow
import com.vlv.common.data.tv_show.TvShowListType
import com.vlv.data.common.model.TimeWindow
import com.vlv.tv_show.data.repository.TvShowRepository
import kotlinx.coroutines.flow.map

class ListingTvShowViewModel(private val repository: TvShowRepository) : ViewModel() {

    private val pagingConfig = PagingConfig(
        pageSize = 20,
        prefetchDistance = 5,
        maxSize = 60,
        initialLoadSize = 20
    )

    fun loadingType(type: TvShowListType) =
        loadByType(type).map {
            it.map { item ->
                TvShow(item)
            }
        }
        .cachedIn(viewModelScope)

    private fun loadByType(type: TvShowListType) = when(type) {
        TvShowListType.TRENDING -> repository.trendingNowPaging(pagingConfig, TimeWindow.DAY)
        TvShowListType.AIRING_TODAY -> repository.airingTodayPaging(pagingConfig)
        TvShowListType.ON_THE_AIR -> repository.onTheAirPaging(pagingConfig)
        TvShowListType.TOP_RATED -> repository.topRatedPaging(pagingConfig)
        TvShowListType.POPULAR -> repository.popularPaging(pagingConfig)
    }


}