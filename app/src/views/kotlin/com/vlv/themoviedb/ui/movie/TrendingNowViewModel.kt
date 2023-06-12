package com.vlv.themoviedb.ui.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vlv.bondsmith.bondsmith
import com.vlv.network.data.movie.MoviesResponse
import com.vlv.network.repository.MovieRepository
import com.vlv.network.repository.TimeWindow

class TrendingNowViewModel(private val repository: MovieRepository) : ViewModel() {

    fun trendingMovies() = bondsmith<MoviesResponse>(viewModelScope)
        .request {
            repository.trendingMovies(TimeWindow.DAY)
        }
        .execute()
        .responseLiveData

}