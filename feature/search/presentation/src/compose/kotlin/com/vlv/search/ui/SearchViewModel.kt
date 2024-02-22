package com.vlv.search.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.vlv.bondsmith.data.flow.ResponseStateFlow
import com.vlv.common.data.movie.Movie
import com.vlv.common.data.people.People
import com.vlv.common.data.series.Series
import com.vlv.data.database.data.History
import com.vlv.data.database.data.HistoryType
import com.vlv.search.domain.usecase.HistoryUseCase
import com.vlv.search.domain.usecase.SearchMovieUseCase
import com.vlv.search.domain.usecase.SearchPeopleUseCase
import com.vlv.search.domain.usecase.SearchSeriesUseCase
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

private const val QUERY_KEY = "QUERY"
private const val SEARCH_TYPE_KEY = "SEARCH_TYPE"

class SearchViewModel(
    private val handle: SavedStateHandle,
    private val historyUseCase: HistoryUseCase,
    private val movieUseCase: SearchMovieUseCase,
    private val seriesUseCase: SearchSeriesUseCase,
    private val peopleUseCase: SearchPeopleUseCase,
) : ViewModel() {

    private val _historyState: MutableStateFlow<List<History>> = MutableStateFlow(listOf())
    val historyState: StateFlow<List<History>>
        get() = _historyState.asStateFlow()

    private val _movieState: MutableStateFlow<PagingData<Movie>> =
        MutableStateFlow(PagingData.empty())

    val movieState: Flow<PagingData<Movie>>
        get() = _movieState.asStateFlow()

    private val _seriesState: MutableStateFlow<PagingData<Series>> =
        MutableStateFlow(PagingData.empty())

    val seriesState: Flow<PagingData<Series>>
        get() = _seriesState.asStateFlow()

    private val _peopleState: MutableStateFlow<PagingData<People>> =
        MutableStateFlow(PagingData.empty())

    val peopleState: Flow<PagingData<People>>
        get() = _peopleState.asStateFlow()

    val searchType: StateFlow<HistoryType>
        get() = handle.getStateFlow(SEARCH_TYPE_KEY, HistoryType.MOVIE)

    val query: StateFlow<String>
        get() = handle.getStateFlow(QUERY_KEY, "")

    private val pagingConfig = PagingConfig(
        pageSize = 20,
        prefetchDistance = 5,
        maxSize = 60,
        initialLoadSize = 20
    )

    fun search(newQuery: String = query.value) {
        viewModelScope.launch(Dispatchers.IO) {
            if(newQuery.isEmpty()) return@launch

            addHistory(newQuery)
            setQuery(newQuery)
            when(searchType.value) {
                HistoryType.MOVIE -> {
                    searchMovie(newQuery)
                }
                HistoryType.SERIES -> {
                    searchSeries(newQuery)
                }
                HistoryType.PEOPLE -> {
                    searchPeople(newQuery)
                }
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private suspend fun searchMovie(query: String) {
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

    @OptIn(ExperimentalCoroutinesApi::class)
    private suspend fun searchSeries(query: String) {
        seriesUseCase.searchSeries(pagingConfig, query)
            .mapLatest {
                it.map(::Series)
            }
            .cachedIn(viewModelScope)
            .distinctUntilChanged()
            .collectLatest {
                _seriesState.emit(it)
            }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private suspend fun searchPeople(query: String) {
        peopleUseCase.searchPeople(pagingConfig, query)
            .mapLatest {
                it.map(::People)
            }
            .cachedIn(viewModelScope)
            .distinctUntilChanged()
            .collectLatest {
                _peopleState.emit(it)
            }
    }

    fun loadHistory() {
        viewModelScope.launch(Dispatchers.IO) {
            updateHistory(historyUseCase.historyAsync(searchType.value))
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

    fun addHistory(query: String) {
        viewModelScope.launch {
            updateHistory(historyUseCase.addHistoryAsync(History(query, searchType.value)))
        }
    }

    fun deleteHistory(history: History) {
        viewModelScope.launch {
            updateHistory(historyUseCase.deleteHistoryAsync(history))
        }
    }

    fun setSearchType(type: Int) {
        viewModelScope.launch {
            runCatching {
                val item = HistoryType.values().get(index = type)
                handle[SEARCH_TYPE_KEY] = item
            }
        }
    }

    fun setSearchType(type: HistoryType) {
        viewModelScope.launch {
            runCatching {
                handle[SEARCH_TYPE_KEY] = type
            }
        }
    }

    fun setQuery(query: String) {
        viewModelScope.launch {
            handle[QUERY_KEY] = query
        }
    }

}