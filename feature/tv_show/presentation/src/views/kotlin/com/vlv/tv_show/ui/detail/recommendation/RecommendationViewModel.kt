package com.vlv.tv_show.ui.detail.recommendation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.map
import com.vlv.common.data.tv_show.TvShow
import com.vlv.tv_show.data.repository.TvShowDetailRepository
import kotlinx.coroutines.flow.map

class RecommendationViewModel(private val repository: TvShowDetailRepository) : ViewModel() {

    private val pagingConfig = PagingConfig(
        pageSize = 20,
        prefetchDistance = 5,
        maxSize = 60,
        initialLoadSize = 20
    )

    fun recommendations(seriesId: Int) = repository
        .tvShowRecommendation(seriesId, pagingConfig)
        .map {
            it.map { seriesResponse ->
                TvShow(seriesResponse)
            }
        }
        .cachedIn(viewModelScope)

}