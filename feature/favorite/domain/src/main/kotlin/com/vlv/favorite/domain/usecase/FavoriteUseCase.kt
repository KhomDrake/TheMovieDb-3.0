package com.vlv.favorite.domain.usecase

import com.vlv.data.database.data.Favorite
import com.vlv.favorite.data.FavoriteRepository

class FavoriteUseCase(
    private val repository: FavoriteRepository
) {

    suspend fun addFavorite(favorite: Favorite) {
        repository.addFavorite(favorite)
    }

    suspend fun removeFavorite(favorite: Favorite) {
        repository.removeFavorite(favorite)
    }

    suspend fun getFavorite(itemId: Int) = repository.getFavorite(itemId)

}