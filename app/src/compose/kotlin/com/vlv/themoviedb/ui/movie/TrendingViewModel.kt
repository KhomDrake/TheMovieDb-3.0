package com.vlv.themoviedb.ui.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vlv.bondsmith.bondsmith
import com.vlv.bondsmith.data.Response
import com.vlv.bondsmith.data.flow.MutableResponseStateFlow
import com.vlv.common.data.movie.Movie
import com.vlv.data.common.model.TimeWindow
import com.vlv.data.common.model.movie.MoviesResponse
import com.vlv.movie.data.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class TrendingViewModel(
    private val repository: MovieRepository
) : ViewModel() {

    init {
        trending()
    }

    val state = MutableResponseStateFlow<List<Movie>>(Response())

    fun trending() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.trendingMovies(TimeWindow.WEEK)
                .responseStateFlow
                .mapData {
                    it?.movies?.map(::Movie) ?: listOf()
                }
                .collectLatest {
                    state.emit(it)
                }
        }
    }

}