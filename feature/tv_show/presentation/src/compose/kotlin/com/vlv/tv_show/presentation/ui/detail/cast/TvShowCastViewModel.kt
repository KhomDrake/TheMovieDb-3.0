package com.vlv.tv_show.presentation.ui.detail.cast

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vlv.bondsmith.data.Response
import com.vlv.common.data.cast.Cast
import com.vlv.tv_show.data.repository.TvShowDetailRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class TvShowCastViewModel(
    private val repository: TvShowDetailRepository
) : ViewModel() {

    private val _state = MutableStateFlow<Response<List<Cast>>>(Response())

    val state: StateFlow<Response<List<Cast>>>
        get() = _state.asStateFlow()

    fun cast(seriesId: Int) {
        viewModelScope.launch {
            repository.tvShowCast(seriesId)
                .responseStateFlow
                .mapData {
                    it?.castResponse?.map(::Cast)
                }
                .collectLatest {
                    _state.emit(it)
                }
        }
    }

}