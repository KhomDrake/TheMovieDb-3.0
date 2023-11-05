package com.vlv.favorite.ui.series

import androidx.lifecycle.ViewModel
import com.vlv.bondsmith.bondsmith
import com.vlv.common.data.series.Series
import com.vlv.data.database.TheMovieDbDao
import com.vlv.data.network.database.data.Favorite
import com.vlv.data.network.database.data.FavoriteType

class SeriesFavoriteViewModel(private val dao: TheMovieDbDao) : ViewModel() {

    fun seriesFavorites(shouldTake: Boolean = false) = bondsmith<List<Favorite>>()
        .request {
            val data = dao.favoriteByType(FavoriteType.SERIES)
            if(shouldTake) data.take(20) else data
        }
        .execute()
        .responseLiveData
        .map {
            it.map(::Series)
        }

}