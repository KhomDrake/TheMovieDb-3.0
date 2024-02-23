package com.vlv.tv_show.presentation.ui.detail.seasons

import android.content.res.Resources
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vlv.bondsmith.data.flow.MutableResponseStateFlow
import com.vlv.bondsmith.data.flow.ResponseStateFlow
import com.vlv.bondsmith.data.flow.asResponseStateFlow
import com.vlv.tv_show.data.repository.TvShowDetailRepository
import com.vlv.tv_show.presentation.model.Season
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SeasonsViewModel(
    private val resources: Resources,
    private val repository: TvShowDetailRepository
) : ViewModel() {

    private val _state = MutableResponseStateFlow<List<Season>>()

    val state: ResponseStateFlow<List<Season>>
        get() = _state.asResponseStateFlow()

    fun seasons(seriesId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.tvShowDetail(seriesId)
                .responseStateFlow
                .mapData { data ->
                    data?.seasons?.map { Season(resources, it) }
                }
                .collectLatest {
                    _state.emit(it)
                }
        }
    }

}