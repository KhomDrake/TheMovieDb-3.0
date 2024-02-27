package com.vlv.favorite.presentation.ui.tvshow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vlv.bondsmith.data.flow.MutableResponseStateFlow
import com.vlv.bondsmith.data.flow.ResponseStateFlow
import com.vlv.bondsmith.data.flow.asResponseStateFlow
import com.vlv.common.data.series.TvShow
import com.vlv.favorite.domain.usecase.TvShowFavoriteUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class TvShowFavoriteViewModel(
    private val useCase: TvShowFavoriteUseCase
) : ViewModel() {

    private val _state = MutableResponseStateFlow<List<TvShow>>()

    val state: ResponseStateFlow<List<TvShow>>
        get() = _state.asResponseStateFlow()

    fun seriesFavorite() {
        viewModelScope.launch(Dispatchers.IO) {
            useCase
                .favorites()
                .responseStateFlow
                .mapData {
                    it?.map(::TvShow) ?: listOf()
                }
                .collectLatest {
                    _state.emit(it)
                }
        }
    }

}