package com.vlv.favorite.domain.usecase

import com.vlv.data.database.data.ItemType
import com.vlv.favorite.data.FavoriteRepository

class TvShowFavoriteUseCase(
    private val repository: FavoriteRepository
) {

    fun favorites() = repository.favoriteByType(
        ItemType.TV_SHOW
    )

}