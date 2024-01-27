package com.vlv.favorite.domain.usecase

import com.vlv.data.database.data.FavoriteType
import com.vlv.favorite.data.FavoriteRepository

class PeopleFavoriteUseCase(
    private val repository: FavoriteRepository
) {

    fun favorites() = repository.favoriteByType(
        FavoriteType.PEOPLE
    )

}