package com.vlv.favorite.presentation.ui.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vlv.bondsmith.bondsmith
import com.vlv.bondsmith.data.Response
import com.vlv.common.data.movie.Movie
import com.vlv.data.database.data.Favorite
import com.vlv.favorite.domain.usecase.MovieFavoriteUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
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
            bondsmith<List<Favorite>>()
                .request {
                    useCase.favorites()
                }
                .execute()
                .stateFlow
                .map {
                    Response(
                        state = it.state,
                        error = it.error,
                        data = it.data?.map(::Movie) ?: listOf()
                    )
                }
                .collectLatest {
                    _state.emit(it)
                }
        }
    }

}