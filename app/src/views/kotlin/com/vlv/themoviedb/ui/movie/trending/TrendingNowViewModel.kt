package com.vlv.themoviedb.ui.movie.trending

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vlv.bondsmith.bondsmith
import com.vlv.common.data.movie.Movie
import com.vlv.data.network.model.movie.MoviesResponse
import com.vlv.data.network.repository.MovieRepository
import com.vlv.data.network.repository.TimeWindow

class TrendingNowViewModel(private val repository: MovieRepository) : ViewModel() {

    fun trendingMovies() = bondsmith<MoviesResponse>(viewModelScope)
        .request {
            repository.trendingMovies(TimeWindow.DAY)
        }
        .execute()
        .responseLiveData
        .map { it.movies.map(::Movie) }

}