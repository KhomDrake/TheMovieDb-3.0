package com.vlv.favorite.ui.movie

import androidx.lifecycle.ViewModel
import com.vlv.bondsmith.bondsmith
import com.vlv.common.data.movie.Movie
import com.vlv.data.database.TheMovieDbDao
import com.vlv.data.network.database.data.Favorite
import com.vlv.data.network.database.data.FavoriteType

class MovieFavoritesViewModel(
    private val dao: TheMovieDbDao
) : ViewModel() {

    fun movieFavorites(shouldTake: Boolean = false) = bondsmith<List<Favorite>>()
        .request {
            val data = dao.favoriteByType(FavoriteType.MOVIE)
            if(shouldTake) data.take(20) else data
        }
        .execute()
        .responseLiveData
        .map {
            it.map(::Movie)
        }

}