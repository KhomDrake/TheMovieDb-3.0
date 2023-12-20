package com.vlv.favorite.presentation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vlv.common.data.movie.Movie
import com.vlv.common.data.series.Series
import com.vlv.favorite.domain.usecase.MovieFavoriteUseCase
import com.vlv.favorite.domain.usecase.PeopleFavoriteUseCase
import com.vlv.favorite.domain.usecase.SeriesFavoriteUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FavoritesViewModel(
    private val movieFavoriteUseCase: MovieFavoriteUseCase,
    private val seriesFavoriteUseCase: SeriesFavoriteUseCase,
    private val peopleFavoriteUseCase: PeopleFavoriteUseCase
) : ViewModel() {

    private val _movieState: MutableStateFlow<List<Movie>> =
        MutableStateFlow(listOf())

    val movieState: Flow<List<Movie>>
        get() = _movieState.asStateFlow()

    fun moviesFavorites() {
        viewModelScope.launch(Dispatchers.IO) {
            val data = movieFavoriteUseCase.favorites()
                .map(::Movie)
            _movieState.emit(data)

        }
    }

    private val _seriesState: MutableStateFlow<List<Series>> =
        MutableStateFlow(listOf())

    val seriesState: Flow<List<Series>>
        get() = _seriesState.asStateFlow()

    fun seriesFavorites() {
        viewModelScope.launch(Dispatchers.IO) {
            val data = seriesFavoriteUseCase.favorites()
                .map(::Series)

            _seriesState.emit(data)
        }
    }

}