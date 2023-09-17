package com.vlv.movie.ui.detail

import androidx.lifecycle.ViewModel
import com.vlv.bondsmith.bondsmith
import com.vlv.common.data.movie.Movie
import com.vlv.common.data.movie.toFavorite
import com.vlv.network.repository.FavoriteRepository

class MovieDetailViewModel(private val repository: FavoriteRepository) : ViewModel() {

    fun addToFavorite(movie: Movie) = bondsmith<Unit>()
        .request {
            repository.addFavorite(movie.toFavorite())
        }
        .execute()
        .responseLiveData

}