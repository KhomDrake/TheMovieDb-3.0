package com.vlv.favorite.presentation.ui.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vlv.bondsmith.data.flow.MutableResponseStateFlow
import com.vlv.bondsmith.data.flow.ResponseStateFlow
import com.vlv.bondsmith.data.flow.asResponseStateFlow
import com.vlv.common.data.movie.Movie
import com.vlv.favorite.domain.usecase.MovieFavoriteUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MovieFavoriteViewModel(
    private val useCase: MovieFavoriteUseCase
) : ViewModel() {

    private val _state = MutableResponseStateFlow<List<Movie>>()

    val state: ResponseStateFlow<List<Movie>>
        get() = _state.asResponseStateFlow()

    fun moviesFavorites() {
        viewModelScope.launch(Dispatchers.IO) {
            useCase
                .favorites()
                .responseStateFlow
                .mapData {
                    it?.map(::Movie) ?: listOf()
                }
                .collectLatest {
                    _state.emit(it)
                }
        }
    }

}