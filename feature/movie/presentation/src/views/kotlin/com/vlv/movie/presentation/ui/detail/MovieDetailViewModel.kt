package com.vlv.movie.presentation.ui.detail

import androidx.lifecycle.ViewModel
import com.vlv.bondsmith.bondsmith
import com.vlv.common.data.movie.Movie
import com.vlv.common.data.movie.toFavorite
import com.vlv.favorite.domain.usecase.FavoriteUseCase

class MovieDetailViewModel(
    private val useCase: FavoriteUseCase
) : ViewModel() {

    fun changeFavorite(movie: Movie) = bondsmith<Boolean>()
        .config {
            request {
                val favorite = useCase.getFavorite(movie.id)
                if(favorite != null) {
                    useCase.removeFavorite(favorite)
                    false
                } else {
                    useCase.addFavorite(movie.toFavorite())
                    true
                }
            }
        }
        .execute()
        .responseLiveData

    fun getFavorite(movieId: Int) = bondsmith<Boolean>()
        .config {
            request {
                useCase.getFavorite(movieId) != null
            }
        }
        .execute()
        .responseLiveData


}