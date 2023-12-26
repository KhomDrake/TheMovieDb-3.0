package com.vlv.favorite.presentation.ui.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vlv.bondsmith.data.Response
import com.vlv.common.data.movie.Movie
import com.vlv.favorite.domain.usecase.MovieFavoriteUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MovieFavoriteViewModel(
    private val useCase: MovieFavoriteUseCase
) : ViewModel() {

    init {
        moviesFavorite()
    }

    private val _state = MutableStateFlow<Response<List<Movie>>>(Response())

    val state: StateFlow<Response<List<Movie>>>
        get() = _state.asStateFlow()

    fun moviesFavorite() {
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