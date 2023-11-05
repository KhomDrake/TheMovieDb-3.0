package com.vlv.favorite.ui.people

import androidx.lifecycle.ViewModel
import com.vlv.bondsmith.bondsmith
import com.vlv.common.data.people.People
import com.vlv.data.network.database.data.Favorite
import com.vlv.favorite.domain.usecase.PeopleFavoriteUseCase

class PeopleFavoritesViewModel(
    private val useCase: PeopleFavoriteUseCase
) : ViewModel() {

    fun peopleFavorites() = bondsmith<List<Favorite>>()
        .request {
            useCase.favorites()
        }
        .execute()
        .responseLiveData
        .map { it.map(::People) }

}