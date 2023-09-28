package com.vlv.people.ui.detail

import androidx.lifecycle.ViewModel
import com.vlv.bondsmith.bondsmith
import com.vlv.common.data.people.People
import com.vlv.common.data.people.toFavorite
import com.vlv.network.repository.FavoriteRepository

class PeopleDetailViewModel(
    private val repository: FavoriteRepository
) : ViewModel() {

    fun getFavorite(peopleId: Int) = bondsmith<Boolean>()
        .request {
            repository.getFavorite(peopleId) != null
        }
        .execute()
        .responseLiveData

    fun changeFavorite(people: People) = bondsmith<Boolean>()
        .request {
            val favorite = repository.getFavorite(people.id)
            if(favorite != null) {
                repository.removeFavorite(favorite)
                false
            } else {
                repository.addFavorite(people.toFavorite())
                true
            }
        }
        .execute()
        .responseLiveData
}