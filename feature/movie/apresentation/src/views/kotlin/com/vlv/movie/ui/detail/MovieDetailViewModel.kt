package com.vlv.movie.ui.detail

import androidx.lifecycle.ViewModel
import com.vlv.bondsmith.bondsmith
import com.vlv.common.data.movie.Movie
import com.vlv.common.data.movie.toFavorite
import com.vlv.data.network.repository.FavoriteRepository

class MovieDetailViewModel(private val repository: FavoriteRepository) : ViewModel() {

    fun changeFavorite(movie: Movie) = bondsmith<Boolean>()
        .request {
            val favorite = repository.getFavorite(movie.id)
            if(favorite != null) {
                repository.removeFavorite(favorite)
                false
            } else {
                repository.addFavorite(movie.toFavorite())
                true
            }
        }
        .execute()
        .responseLiveData

    fun getFavorite(movieId: Int) = bondsmith<Boolean>()
        .request {
            repository.getFavorite(movieId) != null
        }
        .execute()
        .responseLiveData


}