package com.vlv.movie.ui.detail.cast

import androidx.lifecycle.ViewModel
import com.vlv.bondsmith.bondsmith
import com.vlv.common.data.cast.Cast
import com.vlv.data.network.repository.MovieDetailRepository

class CastViewModel(private val repository: MovieDetailRepository) : ViewModel() {

    fun movieCast(movieId: Int) = bondsmith<List<Cast>>()
        .request {
            repository.movieCast(movieId)
                .castResponse.map(::Cast)
        }
        .execute()
        .responseLiveData

}