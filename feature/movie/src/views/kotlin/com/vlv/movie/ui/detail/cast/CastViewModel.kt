package com.vlv.movie.ui.detail.cast

import androidx.lifecycle.ViewModel
import com.vlv.bondsmith.bondsmith
import com.vlv.movie.data.Cast
import com.vlv.network.data.credit.CreditsResponse
import com.vlv.network.repository.MovieDetailRepository

class CastViewModel(private val repository: MovieDetailRepository) : ViewModel() {

    fun movieCast(movieId: Int) = bondsmith<List<Cast>>()
        .request {
            repository.movieCast(movieId)
                .castResponse.map(::Cast)
        }
        .execute()
        .responseLiveData

}