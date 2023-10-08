package com.vlv.search.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.map
import com.vlv.common.data.movie.Movie
import com.vlv.common.data.people.People
import com.vlv.common.data.series.Series
import com.vlv.network.data.movie.MovieResponse
import com.vlv.network.repository.SearchRepository
import com.vlv.search.data.SearchType
import kotlinx.coroutines.flow.map

class SearchViewModel(
    private val searchRepository: SearchRepository
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

    fun setSearchType(type: SearchType) {
        _searchType.postValue(type)
    }

}