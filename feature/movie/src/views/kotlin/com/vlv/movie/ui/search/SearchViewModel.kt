package com.vlv.movie.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.map
import com.vlv.bondsmith.bondsmith
import com.vlv.common.data.movie.Movie
import com.vlv.movie.ui.adapter.HistoryData
import com.vlv.network.database.data.History
import com.vlv.network.database.data.HistoryType
import com.vlv.network.repository.MovieRepository
import com.vlv.network.repository.SearchRepository
import kotlinx.coroutines.flow.map

class SearchViewModel(
    private val repository: MovieRepository,
    private val searchRepository: SearchRepository
) : ViewModel() {

    private val pagingConfig = PagingConfig(
        pageSize = 20,
        prefetchDistance = 5,
        maxSize = 60,
        initialLoadSize = 20
    )

    fun search(query: String) = repository.search(
        pagingConfig,
        query
    )
        .map {
            it.map { movieResponse ->
                Movie(movieResponse)
            }
        }
        .cachedIn(viewModelScope)

    fun addHistory(text: String) = bondsmith<Unit>()
        .request {
            searchRepository.addHistory(History(text, HistoryType.MOVIE))
        }
        .execute()

    fun deleteHistory(text: String) = bondsmith<Unit>()
        .request {
            searchRepository.deleteHistory(History(text, HistoryType.MOVIE))
        }
        .execute()

    fun searchHistory() = searchRepository.history(HistoryType.MOVIE)
        .map { it.map(::HistoryData) }
}