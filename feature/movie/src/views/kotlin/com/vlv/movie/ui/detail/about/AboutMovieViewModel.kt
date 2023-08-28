package com.vlv.movie.ui.detail.about

import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.vlv.bondsmith.bondsmith
import com.vlv.movie.data.MovieDetail
import com.vlv.network.data.movie.MovieDetailResponse
import com.vlv.network.repository.MovieDetailRepository

class AboutMovieViewModel(private val repository: MovieDetailRepository) : ViewModel() {

    fun movieDetail(movieId: Int) = bondsmith<MovieDetailResponse>()
        .request {
            repository.movieDetail(movieId)
        }
        .execute()
        .responseLiveData
        .map {
            MovieDetail(it)
        }

}