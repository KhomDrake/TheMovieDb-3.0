package com.vlv.themoviedb.ui.series

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vlv.bondsmith.data.Response
import com.vlv.common.data.series.TvShow
import com.vlv.data.common.model.TimeWindow
import com.vlv.tv_show.data.repository.TvShowRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SeriesTrendingViewModel(
    private val repository: TvShowRepository
) : ViewModel() {

    init {
        trending()
    }

    val state = MutableStateFlow<Response<List<TvShow>>>(Response())

    fun trending() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.tvShowsTrending(TimeWindow.WEEK)
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