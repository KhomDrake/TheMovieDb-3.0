package com.vlv.series.ui.detail.recommendation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.map
import com.vlv.common.data.series.Series
import com.vlv.series.data.repository.SeriesDetailRepository
import kotlinx.coroutines.flow.map

class RecommendationViewModel(private val repository: SeriesDetailRepository) : ViewModel() {

    private val pagingConfig = PagingConfig(
        pageSize = 20,
        prefetchDistance = 5,
        maxSize = 60,
        initialLoadSize = 20
    )

    fun recommendations(seriesId: Int) = repository.seriesRecommendation(seriesId, pagingConfig)
        .map {
            it.map { seriesResponse ->
                Series(seriesResponse)
            }
        }
        .cachedIn(viewModelScope)

}