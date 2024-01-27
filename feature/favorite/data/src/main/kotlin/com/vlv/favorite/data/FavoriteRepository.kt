package com.vlv.favorite.data

import android.content.res.Resources.NotFoundException
import com.vlv.bondsmith.bondsmith
import com.vlv.data.database.TheMovieDbDao
import com.vlv.data.database.data.Favorite
import com.vlv.data.database.data.FavoriteType

class FavoriteRepository(private val dao: TheMovieDbDao) {

    suspend fun addFavorite(favorite: Favorite) {
        dao.insertFavorite(favorite)
    }

    suspend fun removeFavorite(favorite: Favorite) {
        dao.deleteFavorite(favorite)
    }

    suspend fun getFavorite(itemId: Int) = dao.getFavorite(itemId)

    fun favoriteByType(type: FavoriteType) = bondsmith<List<Favorite>>(type.name)
        .config {
            withCache(with = false)
            request {
                throw NotFoundException()
                dao.favoriteByType(type)
            }
        }
        .execute()


}