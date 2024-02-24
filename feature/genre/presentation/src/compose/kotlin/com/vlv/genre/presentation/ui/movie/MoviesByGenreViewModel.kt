package com.vlv.genre.presentation.ui.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.vlv.common.data.movie.Movie
import com.vlv.genre.domain.usecase.MovieGenreUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class MoviesByGenreViewModel(
    private val genreUseCase: MovieGenreUseCase
) : ViewModel() {

    private val pagingConfig = PagingConfig(
        pageSize = 20,
        prefetchDistance = 5,
        maxSize = 60,
        initialLoadSize = 20
    )

    private val _movieState: MutableStateFlow<PagingData<Movie>> =
        MutableStateFlow(PagingData.empty())

    val movieState: Flow<PagingData<Movie>>
        get() = _movieState.asStateFlow()

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
                    _movieState.emit(it)
                }
        }
    }

}