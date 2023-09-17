package com.vlv.movie.ui.listing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.map
import com.vlv.common.data.movie.Movie
import com.vlv.common.data.movie.MovieListType
import com.vlv.network.repository.MovieRepository
import com.vlv.network.repository.TimeWindow
import kotlinx.coroutines.flow.map

class ListingMovieViewModel(private val repository: MovieRepository) : ViewModel() {

    private val pagingConfig = PagingConfig(
        pageSize = 20,
        prefetchDistance = 5,
        maxSize = 60,
        initialLoadSize = 20
    )

    fun loadMoviesByType(type: MovieListType) = loadByType(type)
        .map {
            it.map { movieResponse ->
                Movie(movieResponse)
            }
        }
        .cachedIn(viewModelScope)

    private fun loadByType(type: MovieListType) = when(type) {
        MovieListType.UPCOMING -> repository.upcomingPaging(pagingConfig)
        MovieListType.TOP_RATED -> repository.topRatedPaging(pagingConfig)
        MovieListType.NOW_PLAYING -> repository.nowPlayingPaging(pagingConfig)
        MovieListType.POPULAR -> repository.popularPaging(pagingConfig)
        MovieListType.TRENDING -> repository.trendingMoviesPaging(pagingConfig, TimeWindow.DAY)
    }


}