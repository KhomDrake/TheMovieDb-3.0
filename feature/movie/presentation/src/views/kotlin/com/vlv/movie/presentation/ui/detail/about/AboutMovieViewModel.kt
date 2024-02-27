package com.vlv.movie.presentation.ui.detail.about

import android.content.res.Resources
import androidx.lifecycle.ViewModel
import com.vlv.movie.data.repository.MovieDetailRepository
import com.vlv.movie.presentation.data.MovieDetail

class AboutMovieViewModel(private val repository: MovieDetailRepository) : ViewModel() {

    fun movieDetail(resources: Resources, movieId: Int) = repository
        .movieDetail(movieId)
        .responseLiveData
        .map {
            MovieDetail(resources, it)
        }

}