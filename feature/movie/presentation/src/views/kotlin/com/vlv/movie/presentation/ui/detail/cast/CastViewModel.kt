package com.vlv.movie.presentation.ui.detail.cast

import androidx.lifecycle.ViewModel
import com.vlv.common.data.cast.Cast
import com.vlv.movie.data.repository.MovieDetailRepository

class CastViewModel(private val repository: MovieDetailRepository) : ViewModel() {

    fun movieCast(movieId: Int) = repository.movieCast(movieId)
        .responseLiveData
        .map {
            it.castResponse.map(::Cast)
        }

}