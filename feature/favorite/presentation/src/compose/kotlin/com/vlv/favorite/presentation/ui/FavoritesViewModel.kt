package com.vlv.favorite.presentation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vlv.bondsmith.data.flow.MutableResponseStateFlow
import com.vlv.bondsmith.data.flow.ResponseStateFlow
import com.vlv.bondsmith.data.flow.asResponseStateFlow
import com.vlv.common.data.movie.Movie
import com.vlv.common.data.people.People
import com.vlv.common.data.series.Series
import com.vlv.favorite.domain.usecase.MovieFavoriteUseCase
import com.vlv.favorite.domain.usecase.PeopleFavoriteUseCase
import com.vlv.favorite.domain.usecase.SeriesFavoriteUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class FavoritesViewModel(
    private val movieFavoriteUseCase: MovieFavoriteUseCase,
    private val seriesFavoriteUseCase: SeriesFavoriteUseCase,
    private val peopleFavoriteUseCase: PeopleFavoriteUseCase
) : ViewModel() {

    private val _movieState: MutableResponseStateFlow<List<Movie>> =
        MutableResponseStateFlow()

    val movieState: ResponseStateFlow<List<Movie>>
        get() = _movieState.asResponseStateFlow()

    fun moviesFavorites() {
        viewModelScope.launch(Dispatchers.IO) {
            movieFavoriteUseCase.favorites()
                .responseStateFlow
                .mapData {
                    it?.map(::Movie) ?: listOf()
                }
                .collectLatest {
                    _movieState.emit(it)
                }
        }
    }

    private val _seriesState: MutableResponseStateFlow<List<Series>> =
        MutableResponseStateFlow()

    val seriesState: ResponseStateFlow<List<Series>>
        get() = _seriesState.asResponseStateFlow()

    fun seriesFavorites() {
        viewModelScope.launch(Dispatchers.IO) {
            seriesFavoriteUseCase.favorites()
                .responseStateFlow
                .mapData {
                    it?.map(::Series) ?: listOf()
                }
                .collectLatest {
                    _seriesState.emit(it)
                }
        }
    }

    private val _peopleState: MutableResponseStateFlow<List<People>> =
        MutableResponseStateFlow()

    val peopleState: ResponseStateFlow<List<People>>
        get() = _peopleState.asResponseStateFlow()

    fun peopleFavorites() {
        viewModelScope.launch(Dispatchers.IO) {
            peopleFavoriteUseCase.favorites()
                .responseStateFlow
                .mapData {
                    it?.map(::People) ?: listOf()
                }
                .collectLatest {
                    _peopleState.emit(it)
                }
        }
    }



}