package com.vlv.search.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.vlv.bondsmith.data.flow.ResponseStateFlow
import com.vlv.common.data.movie.Movie
import com.vlv.data.database.data.History
import com.vlv.data.database.data.HistoryType
import com.vlv.search.domain.usecase.HistoryUseCase
import com.vlv.search.domain.usecase.SearchMovieUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.launch

class SearchViewModel(
    private val historyUseCase: HistoryUseCase,
    private val movieUseCase: SearchMovieUseCase
) : ViewModel() {

    private val _historyState: MutableStateFlow<List<History>> = MutableStateFlow(listOf())
    val historyState: StateFlow<List<History>>
        get() = _historyState.asStateFlow()

    private val _movieState: MutableStateFlow<PagingData<Movie>> =
        MutableStateFlow(PagingData.empty())

    val movieState: Flow<PagingData<Movie>>
        get() = _movieState.asStateFlow()

    private val pagingConfig = PagingConfig(
        pageSize = 20,
        prefetchDistance = 5,
        maxSize = 60,
        initialLoadSize = 20
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    fun movies(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            movieUseCase.searchMovie(pagingConfig, query)
                .mapLatest {
                    it.map(::Movie)
                }
                .cachedIn(viewModelScope)
                .distinctUntilChanged()
                .collectLatest {
                    _movieState.emit(it)
                }
        }
    }

    fun loadHistory(historyType: HistoryType) {
        viewModelScope.launch(Dispatchers.IO) {
            updateHistory(historyUseCase.historyAsync(historyType))
        }
    }

    private suspend fun updateHistory(stateFlow: ResponseStateFlow<List<History>>) {
        stateFlow
            .collect(
                dataCollector = {
                    _historyState.emit(it)
                },
                errorCollector = {
                    _historyState.emit(listOf())
                }
            )
    }

    fun addHistory(history: History) {
        viewModelScope.launch {
            updateHistory(historyUseCase.addHistory2(history))
        }
    }

}