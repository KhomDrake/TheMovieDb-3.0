package com.vlv.themoviedb.ui.movie.trending

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vlv.bondsmith.bondsmith
import com.vlv.common.data.movie.Movie
import com.vlv.data.common.model.TimeWindow
import com.vlv.data.common.model.movie.MoviesResponse
import com.vlv.movie.data.repository.MovieRepository

class TrendingNowViewModel(private val repository: MovieRepository) : ViewModel() {

    fun trendingMovies() = bondsmith<MoviesResponse>(viewModelScope)
        .request {
            repository.trendingMovies(TimeWindow.DAY)
        }
        .execute()
        .responseLiveData
        .map {
            it.movies.map(::Movie)
        }

}