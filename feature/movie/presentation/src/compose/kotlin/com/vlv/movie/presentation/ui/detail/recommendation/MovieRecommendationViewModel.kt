package com.vlv.movie.presentation.ui.detail.recommendation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.vlv.common.data.movie.Movie
import com.vlv.movie.data.repository.MovieDetailRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class MovieRecommendationViewModel(
    private val repository: MovieDetailRepository
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

    fun movieRecommendation(movieId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.movieRecommendations(pagingConfig, movieId)
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

}