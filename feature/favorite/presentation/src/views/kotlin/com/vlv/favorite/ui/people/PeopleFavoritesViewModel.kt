package com.vlv.favorite.ui.people

import androidx.lifecycle.ViewModel
import com.vlv.common.data.people.People
import com.vlv.favorite.domain.usecase.PeopleFavoriteUseCase

class PeopleFavoritesViewModel(
    private val useCase: PeopleFavoriteUseCase
) : ViewModel() {

    fun peopleFavorites() = useCase.favorites()
        .responseLiveData
        .map { it.map(::People) }

}