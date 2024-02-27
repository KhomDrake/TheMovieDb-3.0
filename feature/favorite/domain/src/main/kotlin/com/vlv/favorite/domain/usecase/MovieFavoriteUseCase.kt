package com.vlv.favorite.domain.usecase

import com.vlv.data.database.data.ItemType
import com.vlv.favorite.data.FavoriteRepository

class MovieFavoriteUseCase(
    private val repository: FavoriteRepository
) {
    fun favorites() = repository.favoriteByType(
        ItemType.MOVIE
    )

}