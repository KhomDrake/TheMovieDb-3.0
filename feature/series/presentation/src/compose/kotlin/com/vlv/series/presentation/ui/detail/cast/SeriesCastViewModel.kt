package com.vlv.series.presentation.ui.detail.cast

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vlv.bondsmith.bondsmith
import com.vlv.bondsmith.data.Response
import com.vlv.bondsmith.mapData
import com.vlv.common.data.cast.Cast
import com.vlv.data.common.model.credit.CastResponse
import com.vlv.series.data.repository.SeriesDetailRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SeriesCastViewModel(
    private val repository: SeriesDetailRepository
) : ViewModel() {

    private val _state = MutableStateFlow<Response<List<Cast>>>(Response())

    val state: StateFlow<Response<List<Cast>>>
        get() = _state.asStateFlow()

    fun cast(seriesId: Int) {
        viewModelScope.launch {
            bondsmith<List<CastResponse>>()
                .request {
                    repository.seriesCast(seriesId).castResponse
                }
                .execute()
                .responseStateFlow
                .mapData {
                    it?.map(::Cast)
                }
                .collectLatest {
                    _state.emit(it)
                }
        }
    }

}