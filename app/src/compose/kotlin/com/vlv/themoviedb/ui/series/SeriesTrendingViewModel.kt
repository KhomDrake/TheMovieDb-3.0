package com.vlv.themoviedb.ui.series

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vlv.bondsmith.bondsmith
import com.vlv.bondsmith.data.Response
import com.vlv.common.data.series.Series
import com.vlv.data.common.model.TimeWindow
import com.vlv.data.common.model.series.SeriesResponse
import com.vlv.series.data.repository.SeriesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SeriesTrendingViewModel(
    private val repository: SeriesRepository
) : ViewModel() {

    init {
        trending()
    }

    val state = MutableStateFlow<Response<List<Series>>>(Response())

    fun trending() {
        viewModelScope.launch(Dispatchers.IO) {
            bondsmith<SeriesResponse>()
                .request {
                    repository.trendingSeries(TimeWindow.WEEK)
                }
                .execute()
                .responseStateFlow
                .collectLatest {
                    state.emit(
                        Response(
                            state = it.state,
                            error = it.error,
                            data = it.data?.series?.map(::Series) ?: listOf()
                        )
                    )
                }
        }
    }

}