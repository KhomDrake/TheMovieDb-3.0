package com.vlv.themoviedb.ui.series

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vlv.bondsmith.data.flow.MutableResponseStateFlow
import com.vlv.common.data.series.Series
import com.vlv.series.data.repository.SeriesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class AiringTodayViewModel(
    private val repository: SeriesRepository
) : ViewModel() {

    init {
        airingToday()
    }

    val state = MutableResponseStateFlow<List<Series>>()

    fun airingToday() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.airingToday()
                .responseStateFlow
                .mapData {
                    it?.series?.map(::Series) ?: listOf()
                }
                .collectLatest {
                    state.emit(it)
                }
        }
    }

}