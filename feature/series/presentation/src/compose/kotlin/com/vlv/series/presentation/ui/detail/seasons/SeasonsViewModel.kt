package com.vlv.series.presentation.ui.detail.seasons

import android.content.res.Resources
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vlv.bondsmith.bondsmith
import com.vlv.bondsmith.data.Response
import com.vlv.bondsmith.mapData
import com.vlv.data.common.model.series.SeasonResponse
import com.vlv.series.data.repository.SeriesDetailRepository
import com.vlv.series.presentation.model.Season
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SeasonsViewModel(
    private val resources: Resources,
    private val repository: SeriesDetailRepository
) : ViewModel() {

    private val _state = MutableStateFlow<Response<List<Season>>>(Response())

    val state: StateFlow<Response<List<Season>>>
        get() = _state.asStateFlow()

    fun seasons(seriesId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            bondsmith<List<SeasonResponse>>()
                .request {
                    repository.seriesDetail(seriesId).seasons
                }
                .execute()
                .responseStateFlow
                .mapData { data ->
                    data?.map { Season(resources, it) }
                }
                .collectLatest {
                    _state.emit(it)
                }
        }
    }

}