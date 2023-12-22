package com.vlv.series.presentation.ui.detail.about

import android.content.res.Resources
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vlv.bondsmith.bondsmith
import com.vlv.bondsmith.data.Response
import com.vlv.bondsmith.mapData
import com.vlv.data.common.model.series.SeriesDetailResponse
import com.vlv.series.data.repository.SeriesDetailRepository
import com.vlv.series.presentation.model.SeriesDetail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class AboutSeriesViewModel(
    private val resources: Resources,
    private val repository: SeriesDetailRepository
) : ViewModel() {

    private val _state = MutableStateFlow<Response<SeriesDetail>>(Response())

    val state: StateFlow<Response<SeriesDetail>>
        get() = _state.asStateFlow()

    fun seriesDetail(seriesId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            bondsmith<SeriesDetailResponse>()
                .request {
                    repository.seriesDetail(seriesId)
                }
                .execute()
                .stateFlow
                .mapData {
                    it?.let {
                        SeriesDetail(resources, it)
                    }
                }
                .collectLatest {
                    _state.emit(it)
                }
        }
    }

}