package com.vlv.search.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.map
import br.com.arch.toolkit.livedata.extention.mapList
import com.vlv.common.data.movie.Movie
import com.vlv.common.data.people.People
import com.vlv.common.data.tv_show.TvShow
import com.vlv.common.ui.adapter.searchhistory.HistoryItems
import com.vlv.data.common.model.movie.MovieResponse
import com.vlv.data.database.data.History
import com.vlv.data.database.data.ItemType
import com.vlv.search.data.SearchType
import com.vlv.search.domain.usecase.HistoryUseCase
import com.vlv.search.domain.usecase.SearchMovieUseCase
import com.vlv.search.domain.usecase.SearchPeopleUseCase
import com.vlv.search.domain.usecase.SearchTvShowsUseCase
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class SearchViewModel(
    private val movieUseCase: SearchMovieUseCase,
    private val seriesUseCase: SearchTvShowsUseCase,
    private val peopleUseCase: SearchPeopleUseCase,
    private val historyUseCase: HistoryUseCase
) : ViewModel() {

    private val _searchType = MutableLiveData<SearchType>()
    val searchType: LiveData<SearchType>
        get() = _searchType

    init {
        _searchType.postValue(SearchType.MOVIE)
    }

    private val pagingConfig = PagingConfig(
        pageSize = 20,
        prefetchDistance = 5,
        maxSize = 60,
        initialLoadSize = 20
    )

    fun searchMovie(query: String) = movieUseCase
        .searchMovie(pagingConfig, query)
        .map {
            it.map { movieResponse: MovieResponse -> Movie(movieResponse) }
        }
        .cachedIn(viewModelScope)

    fun searchSeries(query: String) = seriesUseCase
        .searchTvShows(pagingConfig, query)
        .map {
            it.map { seriesItem -> TvShow(seriesItem) }
        }
        .cachedIn(viewModelScope)

    fun searchPeople(query: String) = peopleUseCase
        .searchPeople(pagingConfig, query)
        .map {
            it.map { peopleItem -> People(peopleItem) }
        }
        .cachedIn(viewModelScope)

    fun setSearchType(type: SearchType) {
        _searchType.postValue(type)
    }

    fun historyBySearchType(title: String) = run {
        val searchType = _searchType.value ?: SearchType.MOVIE
        val historyType = ItemType.values()[searchType.ordinal]
        historyUseCase.history(historyType)
    }.mapList {
        HistoryItems.HistoryItem(it) as HistoryItems
    }.map {
        it.toMutableList().apply {
            if(isNotEmpty()) add(0, HistoryItems.HistoryTitle(title))
        }.toList()
    }

    fun addToHistory(query: String) {
        viewModelScope.launch {
            val searchType = _searchType.value ?: SearchType.MOVIE
            val historyType = ItemType.values()[searchType.ordinal]
            historyUseCase.addHistory(
                History(query, historyType)
            )
        }
    }

    fun removeHistory(history: History) {
        viewModelScope.launch {
            historyUseCase.deleteHistory(history)
        }
    }

}