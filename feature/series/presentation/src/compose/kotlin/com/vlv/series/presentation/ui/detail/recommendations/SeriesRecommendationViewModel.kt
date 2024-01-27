package com.vlv.series.presentation.ui.detail.recommendations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.vlv.common.data.series.Series
import com.vlv.series.data.repository.SeriesDetailRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class SeriesRecommendationViewModel(
    private val repository: SeriesDetailRepository
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

    fun seriesRecommendation(seriesId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.seriesRecommendation(seriesId, pagingConfig)
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

}