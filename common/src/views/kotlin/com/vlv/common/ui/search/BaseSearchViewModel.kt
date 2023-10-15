package com.vlv.common.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.map
import com.vlv.common.data.movie.Movie
import com.vlv.common.data.people.People
import com.vlv.common.data.series.Series
import com.vlv.network.data.movie.MovieResponse
import com.vlv.network.database.data.History
import com.vlv.network.database.data.HistoryType
import com.vlv.network.repository.SearchRepository
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

abstract class BaseSearchViewModel(
    private val searchRepository: SearchRepository
) : ViewModel() {

    abstract val historyType: HistoryType

    private val pagingConfig = PagingConfig(
        pageSize = 20,
        prefetchDistance = 5,
        maxSize = 60,
        initialLoadSize = 20
    )

    fun searchMovie(query: String) = searchRepository
        .searchMovie(pagingConfig, query)
        .map {
            it.map { movieResponse: MovieResponse -> Movie(movieResponse) }
        }
        .cachedIn(viewModelScope)

    fun searchSeries(query: String) = searchRepository
        .searchSeries(pagingConfig, query)
        .map {
            it.map { seriesItem -> Series(seriesItem) }
        }
        .cachedIn(viewModelScope)

    fun searchPeople(query: String) = searchRepository
        .searchPeople(pagingConfig, query)
        .map {
            it.map { peopleItem -> People(peopleItem) }
        }
        .cachedIn(viewModelScope)

    fun historyBySearchType() = run {
        searchRepository.history(historyType)
    }

    fun addToHistory(query: String) {
        viewModelScope.launch {
            searchRepository.addHistory(
                History(query, historyType)
            )
        }
    }

    fun removeHistory(history: History) {
        viewModelScope.launch {
            searchRepository.deleteHistory(history)
        }
    }

}