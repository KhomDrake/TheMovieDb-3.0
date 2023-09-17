package com.vlv.favorite.ui.movie

import androidx.lifecycle.ViewModel
import com.vlv.bondsmith.bondsmith
import com.vlv.common.data.movie.Movie
import com.vlv.network.database.TheMovieDbDao
import com.vlv.network.database.data.Favorite
import com.vlv.network.database.data.FavoriteType

class MovieFavoritesViewModel(
    private val dao: TheMovieDbDao
) : ViewModel() {

    fun movieFavorites() = bondsmith<List<Favorite>>()
        .request {
            dao.favoriteByType(FavoriteType.MOVIE)
        }
        .execute()
        .responseLiveData
        .map {
            it.map(::Movie)
        }

}