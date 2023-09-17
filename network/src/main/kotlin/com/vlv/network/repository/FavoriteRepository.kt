package com.vlv.network.repository

import com.vlv.network.database.TheMovieDbDao
import com.vlv.network.database.data.Favorite
import com.vlv.network.database.data.FavoriteType

class FavoriteRepository(private val dao: TheMovieDbDao) {

    suspend fun addFavorite(favorite: Favorite) {
        dao.insertFavorite(favorite)
    }

    suspend fun favoriteByType(type: FavoriteType) = dao.favoriteByType(type)

}