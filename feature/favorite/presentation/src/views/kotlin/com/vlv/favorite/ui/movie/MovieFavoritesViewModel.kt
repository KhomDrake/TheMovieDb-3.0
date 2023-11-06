package com.vlv.favorite.ui.movie

import androidx.lifecycle.ViewModel
import com.vlv.bondsmith.bondsmith
import com.vlv.common.data.movie.Movie
import com.vlv.data.network.database.data.Favorite
import com.vlv.favorite.domain.usecase.MovieFavoriteUseCase

class MovieFavoritesViewModel(
    private val useCase: MovieFavoriteUseCase
) : ViewModel() {

    fun movieFavorites(shouldTake: Boolean = false) = bondsmith<List<Favorite>>()
        .request {
            val data = useCase.favorites()
            if(shouldTake) data.take(20) else data
        }
        .execute()
        .responseLiveData
        .map {
            it.map(::Movie)
        }

}