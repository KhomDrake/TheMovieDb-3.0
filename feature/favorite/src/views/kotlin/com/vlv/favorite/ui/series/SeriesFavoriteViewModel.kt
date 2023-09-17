package com.vlv.favorite.ui.series

import androidx.lifecycle.ViewModel
import com.vlv.bondsmith.bondsmith
import com.vlv.common.data.series.Series
import com.vlv.network.database.TheMovieDbDao
import com.vlv.network.database.data.Favorite
import com.vlv.network.database.data.FavoriteType

class SeriesFavoriteViewModel(private val dao: TheMovieDbDao) : ViewModel() {

    fun seriesFavorites() = bondsmith<List<Favorite>>()
        .request {
            dao.favoriteByType(FavoriteType.SERIES)
        }
        .execute()
        .responseLiveData
        .map {
            it.map(::Series)
        }

}