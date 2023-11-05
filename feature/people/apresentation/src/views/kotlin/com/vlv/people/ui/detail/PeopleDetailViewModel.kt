package com.vlv.people.ui.detail

import androidx.lifecycle.ViewModel
import com.vlv.bondsmith.bondsmith
import com.vlv.common.data.people.People
import com.vlv.common.data.people.toFavorite
import com.vlv.favorite.domain.usecase.FavoriteUseCase

class PeopleDetailViewModel(
    private val useCase: FavoriteUseCase
) : ViewModel() {

    fun getFavorite(peopleId: Int) = bondsmith<Boolean>()
        .request {
            useCase.getFavorite(peopleId) != null
        }
        .execute()
        .responseLiveData

    fun changeFavorite(people: People) = bondsmith<Boolean>()
        .request {
            val favorite = useCase.getFavorite(people.id)
            if(favorite != null) {
                useCase.removeFavorite(favorite)
                false
            } else {
                useCase.addFavorite(people.toFavorite())
                true
            }
        }
        .execute()
        .responseLiveData
}