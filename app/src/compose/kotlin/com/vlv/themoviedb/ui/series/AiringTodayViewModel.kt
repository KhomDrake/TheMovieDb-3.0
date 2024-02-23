package com.vlv.themoviedb.ui.series

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vlv.bondsmith.data.flow.MutableResponseStateFlow
import com.vlv.common.data.series.TvShow
import com.vlv.tv_show.data.repository.TvShowRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class AiringTodayViewModel(
    private val repository: TvShowRepository
) : ViewModel() {

    init {
        airingToday()
    }

    val state = MutableResponseStateFlow<List<TvShow>>()

    fun airingToday() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.airingToday()
                .responseStateFlow
                .mapData {
                    it?.tvShows?.map(::TvShow) ?: listOf()
                }
                .collectLatest {
                    state.emit(it)
                }
        }
    }

}