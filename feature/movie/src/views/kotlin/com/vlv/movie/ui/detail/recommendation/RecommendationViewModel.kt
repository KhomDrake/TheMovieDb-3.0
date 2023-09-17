package com.vlv.movie.ui.detail.recommendation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.map
import com.vlv.common.data.movie.Movie
import com.vlv.network.repository.MovieDetailRepository
import kotlinx.coroutines.flow.map

class RecommendationViewModel(private val repository: MovieDetailRepository) : ViewModel() {

    private val pagingConfig = PagingConfig(
        pageSize = 20,
        prefetchDistance = 5,
        maxSize = 60,
        initialLoadSize = 20
    )

    fun recommendations(movieId: Int) = repository.movieRecommendations(pagingConfig, movieId)
        .map {
            it.map { movieResponse ->
                Movie(movieResponse)
            }
        }
        .cachedIn(viewModelScope)

}