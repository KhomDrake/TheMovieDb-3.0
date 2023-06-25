package com.vlv.movie.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.map
import com.vlv.movie.data.Movie
import com.vlv.network.repository.MovieRepository
import com.vlv.network.repository.TimeWindow
import kotlinx.coroutines.flow.map

class TrendingNowViewModel(private val repository: MovieRepository) : ViewModel() {

    private val pagingConfig = PagingConfig(
        pageSize = 20,
        prefetchDistance = 5,
        maxSize = 60,
        initialLoadSize = 20
    )

    fun trendingNow() = repository.trendingMoviesPaging(
        pagingConfig,
        TimeWindow.DAY
    )
        .map {
            it.map { movieResponse ->
                Movie(movieResponse)
            }
        }
        .cachedIn(viewModelScope)

}