package com.vlv.genre.presentation.ui.series

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.vlv.bondsmith.bondsmith
import com.vlv.bondsmith.data.Response
import com.vlv.common.data.series.Series
import com.vlv.data.common.model.genre.GenresResponse
import com.vlv.genre.domain.usecase.SeriesGenreUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SeriesGenreViewModel(
    private val useCase: SeriesGenreUseCase
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

    private val _flow: MutableStateFlow<PagingData<Series>> =
        MutableStateFlow(PagingData.empty())

    val flow: Flow<PagingData<Series>>
        get() = _flow.asStateFlow()

    fun moviesByGenre(genreId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            useCase.seriesByGenre(
                pagingConfig,
                genreId
            )
                .map {
                    it.map(::Series)
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
                bondsmith<GenresResponse>()
                    .request {
                        useCase.genres()
                    }
                    .execute()
                    .stateFlow
                    .collectLatest {
                        _state.emit(it)
                    }
            }
        }
    }

}