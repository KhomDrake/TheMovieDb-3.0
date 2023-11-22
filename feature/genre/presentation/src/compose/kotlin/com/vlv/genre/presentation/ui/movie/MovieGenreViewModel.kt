package com.vlv.genre.presentation.ui.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.vlv.bondsmith.bondsmith
import com.vlv.bondsmith.data.Response
import com.vlv.data.common.model.genre.GenresResponse
import com.vlv.data.common.model.movie.MovieResponse
import com.vlv.genre.domain.usecase.MovieGenreUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieGenreViewModel(
    private val genreUseCase: MovieGenreUseCase
) : ViewModel() {

    init {
        loadGenres()
    }

    private val _state = MutableStateFlow<Response<GenresResponse>>(Response())
    val state: StateFlow<Response<GenresResponse>>
        get() = _state.asStateFlow()

    private val pagingConfig = PagingConfig(
        pageSize = 20,
        prefetchDistance = 5,
        maxSize = 60,
        initialLoadSize = 20
    )

    private val _flow: MutableStateFlow<PagingData<MovieResponse>> =
        MutableStateFlow(PagingData.empty())

    val flow: Flow<PagingData<MovieResponse>>
        get() = _flow.asStateFlow()

    fun moviesByGenre(genreId: Int){
        viewModelScope.launch(Dispatchers.IO) {
            genreUseCase.moviesByGenre(
                pagingConfig,
                genreId
            )
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
                bondsmith<GenresResponse>()
                    .request {
                        genreUseCase.genres()
                    }
                    .execute()
                    .responseStateFlow
                    .collectLatest {
                        _state.emit(it)
                    }
            }
        }
    }

}