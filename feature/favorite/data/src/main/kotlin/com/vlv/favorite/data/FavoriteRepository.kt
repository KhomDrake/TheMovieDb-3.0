package com.vlv.favorite.data

import com.vlv.bondsmith.bondsmith
import com.vlv.data.database.TheMovieDbDao
import com.vlv.data.database.data.Favorite
import com.vlv.data.database.data.ItemType

class FavoriteRepository(private val dao: TheMovieDbDao) {

    suspend fun addFavorite(favorite: Favorite) {
        dao.insertFavorite(favorite)
    }

    suspend fun removeFavorite(favorite: Favorite) {
        dao.deleteFavorite(favorite)
    }

    suspend fun getFavorite(itemId: Int) = dao.getFavorite(itemId)

    fun favoriteByType(type: ItemType) = bondsmith<List<Favorite>>(type.name)
        .config {
            request {
                dao.favoriteByType(type)
            }
        }
        .execute()


}