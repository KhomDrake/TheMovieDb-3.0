package com.vlv.tv_show.presentation.ui.detail.about

import android.content.res.Resources
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vlv.bondsmith.data.Response
import com.vlv.tv_show.data.repository.TvShowDetailRepository
import com.vlv.tv_show.presentation.model.TvShowDetail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class AboutTvShowViewModel(
    private val resources: Resources,
    private val repository: TvShowDetailRepository
) : ViewModel() {

    private val _state = MutableStateFlow<Response<TvShowDetail>>(Response())

    val state: StateFlow<Response<TvShowDetail>>
        get() = _state.asStateFlow()

    fun seriesDetail(seriesId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.tvShowDetail(seriesId)
                .responseStateFlow
                .mapData {
                    it?.let {
                        TvShowDetail(resources, it)
                    }
                }
                .collectLatest {
                    _state.emit(it)
                }
        }
    }

}