package com.vlv.favorite.ui.movie

import androidx.lifecycle.ViewModel
import com.vlv.common.data.movie.Movie
import com.vlv.favorite.domain.usecase.MovieFavoriteUseCase

class MovieFavoritesViewModel(
    private val useCase: MovieFavoriteUseCase
) : ViewModel() {

    fun movieFavorites(shouldTake: Boolean = false) = useCase
        .favorites()
        .responseLiveData
        .map {
            val items = if(shouldTake) it.take(20) else it
            items.map(::Movie)
        }

}