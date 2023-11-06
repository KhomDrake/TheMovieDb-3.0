package com.vlv.movie.ui.detail.about

import android.content.res.Resources
import androidx.lifecycle.ViewModel
import com.vlv.bondsmith.bondsmith
import com.vlv.data.common.model.movie.MovieDetailResponse
import com.vlv.movie.data.MovieDetail
import com.vlv.movie.data.repository.MovieDetailRepository

class AboutMovieViewModel(private val repository: MovieDetailRepository) : ViewModel() {

    fun movieDetail(resources: Resources, movieId: Int) = bondsmith<MovieDetailResponse>()
        .request {
            repository.movieDetail(movieId)
        }
        .execute()
        .responseLiveData
        .map {
            MovieDetail(resources, it)
        }

}