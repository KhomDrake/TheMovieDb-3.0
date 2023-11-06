package com.vlv.favorite.domain.usecase

import com.vlv.data.network.database.data.FavoriteType
import com.vlv.favorite.data.FavoriteRepository

class MovieFavoriteUseCase(
    private val repository: FavoriteRepository
) {
    suspend fun favorites() = repository.favoriteByType(
        FavoriteType.MOVIE
    )

}