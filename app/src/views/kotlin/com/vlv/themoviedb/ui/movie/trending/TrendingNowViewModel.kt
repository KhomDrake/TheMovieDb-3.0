package com.vlv.themoviedb.ui.movie.trending

import androidx.lifecycle.ViewModel
import com.vlv.common.data.movie.Movie
import com.vlv.data.common.model.TimeWindow
import com.vlv.movie.data.repository.MovieRepository

class TrendingNowViewModel(private val repository: MovieRepository) : ViewModel() {

    fun trendingMovies() = repository
        .trendingMovies(TimeWindow.DAY)
        .responseLiveData
        .map {
            it.movies.map(::Movie)
        }

}