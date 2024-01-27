package com.vlv.favorite.presentation.ui.series

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vlv.bondsmith.data.flow.MutableResponseStateFlow
import com.vlv.bondsmith.data.flow.ResponseStateFlow
import com.vlv.bondsmith.data.flow.asResponseStateFlow
import com.vlv.common.data.series.Series
import com.vlv.favorite.domain.usecase.SeriesFavoriteUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SeriesFavoriteViewModel(
    private val useCase: SeriesFavoriteUseCase
) : ViewModel() {

    private val _state = MutableResponseStateFlow<List<Series>>()

    val state: ResponseStateFlow<List<Series>>
        get() = _state.asResponseStateFlow()

    fun seriesFavorite() {
        viewModelScope.launch(Dispatchers.IO) {
            useCase
                .favorites()
                .responseStateFlow
                .mapData {
                    it?.map(::Series) ?: listOf()
                }
                .collectLatest {
                    _state.emit(it)
                }
        }
    }

}