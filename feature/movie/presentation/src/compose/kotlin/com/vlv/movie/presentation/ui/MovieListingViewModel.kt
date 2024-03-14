package com.vlv.movie.presentation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.vlv.common.data.movie.Movie
import com.vlv.common.data.movie.MovieListType
import com.vlv.data.common.model.TimeWindow
import com.vlv.movie.data.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class MovieListingViewModel(
    private val repository: MovieRepository
) : ViewModel() {

    private val _state: MutableStateFlow<PagingData<Movie>> =
        MutableStateFlow(PagingData.empty())

    val state: Flow<PagingData<Movie>>
        get() = _state

    private val pagingConfig = PagingConfig(
        pageSize = 20,
        prefetchDistance = 5,
        maxSize = 60,
        initialLoadSize = 20
    )

    fun movies(movieListType: MovieListType){
        viewModelScope.launch(Dispatchers.IO) {
            moviesByListType(movieListType)
                .map {
                    it.map(::Movie)
                }
                .cachedIn(viewModelScope)
                .distinctUntilChanged()
                .collectLatest {
                    _state.emit(it)
                }
        }
    }

    private fun moviesByListType(movieListType: MovieListType) =
        when(movieListType) {
            MovieListType.TRENDING -> repository.trendingMoviesPaging(
                pagingConfig,
                TimeWindow.WEEK
            )
            MovieListType.NOW_PLAYING -> repository.nowPlayingPaging(pagingConfig)
            MovieListType.POPULAR -> repository.popularPaging(pagingConfig)
            MovieListType.TOP_RATED -> repository.topRatedPaging(pagingConfig)
            MovieListType.UPCOMING -> repository.upcomingPaging(pagingConfig)
        }

}