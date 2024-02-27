package com.vlv.genre.presentation.ui.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.map
import com.vlv.common.data.movie.Movie
import com.vlv.genre.domain.usecase.MovieGenreUseCase
import com.vlv.genre.presentation.data.Genre
import kotlinx.coroutines.flow.map

class MovieGenreViewModel(
    private val movieUseCase: MovieGenreUseCase
) : ViewModel() {

    private val pagingConfig = PagingConfig(
        pageSize = 20,
        prefetchDistance = 5,
        maxSize = 60,
        initialLoadSize = 20
    )

    fun genres() = movieUseCase.genres()
        .responseLiveData
        .map { it.map(::Genre) }

    fun moviesByGenre(genreId: Int) = movieUseCase.moviesByGenre(pagingConfig, genreId)
        .map {
            it.map { movieResponse ->
                Movie(movieResponse)
            }
        }
        .cachedIn(viewModelScope)

}