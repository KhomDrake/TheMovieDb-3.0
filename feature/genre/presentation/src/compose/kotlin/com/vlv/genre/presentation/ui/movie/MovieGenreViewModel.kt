package com.vlv.genre.presentation.ui.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.vlv.bondsmith.data.Response
import com.vlv.bondsmith.data.flow.MutableResponseStateFlow
import com.vlv.bondsmith.data.flow.ResponseStateFlow
import com.vlv.bondsmith.data.flow.asResponseStateFlow
import com.vlv.common.data.movie.Movie
import com.vlv.data.common.model.genre.GenreResponse
import com.vlv.genre.domain.usecase.MovieGenreUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieGenreViewModel(
    private val genreUseCase: MovieGenreUseCase
) : ViewModel() {

    init {
        loadGenres()
    }

    private val _state = MutableResponseStateFlow<List<GenreResponse>>(Response())
    val state: ResponseStateFlow<List<GenreResponse>>
        get() = _state.asResponseStateFlow()

    private val pagingConfig = PagingConfig(
        pageSize = 20,
        prefetchDistance = 5,
        maxSize = 60,
        initialLoadSize = 20
    )

    private val _flow: MutableStateFlow<PagingData<Movie>> =
        MutableStateFlow(PagingData.empty())

    val flow: Flow<PagingData<Movie>>
        get() = _flow.asStateFlow()

    fun moviesByGenre(genreId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            genreUseCase.moviesByGenre(
                pagingConfig,
                genreId
            )
                .map {
                    it.map(::Movie)
                }
                .cachedIn(viewModelScope)
                .distinctUntilChanged()
                .collectLatest {
                    _flow.emit(it)
                }
        }
    }

    private fun loadGenres() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                genreUseCase.genres()
                    .responseStateFlow
                    .collectLatest {
                        _state.emit(it)
                    }
            }
        }
    }

}