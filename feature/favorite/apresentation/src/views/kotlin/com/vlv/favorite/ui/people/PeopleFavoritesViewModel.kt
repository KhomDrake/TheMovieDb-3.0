package com.vlv.favorite.ui.people

import androidx.lifecycle.ViewModel
import com.vlv.bondsmith.bondsmith
import com.vlv.common.data.people.People
import com.vlv.data.network.database.data.Favorite
import com.vlv.data.network.database.data.FavoriteType
import com.vlv.data.network.repository.FavoriteRepository

class PeopleFavoritesViewModel(
    private val repository: FavoriteRepository
) : ViewModel() {

    fun peopleFavorites() = bondsmith<List<Favorite>>()
        .request {
            repository.favoriteByType(FavoriteType.PEOPLE)
        }
        .execute()
        .responseLiveData
        .map { it.map(::People) }

}