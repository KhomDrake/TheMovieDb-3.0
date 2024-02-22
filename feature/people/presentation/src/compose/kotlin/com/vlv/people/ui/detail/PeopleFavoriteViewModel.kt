package com.vlv.people.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vlv.common.data.people.People
import com.vlv.common.data.people.toFavorite
import com.vlv.favorite.domain.usecase.FavoriteUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PeopleFavoriteViewModel(
    private val useCase: FavoriteUseCase
) : ViewModel() {

    fun isFavorite(people: People) {
        viewModelScope.launch(Dispatchers.IO) {
            val isFavorite = useCase.getFavorite(people.id) != null
            _favoriteState.emit(isFavorite)
        }
    }

    fun changeFavorite(people: People, favorite: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            if(favorite) {
                useCase.removeFavorite(people.toFavorite())
            } else {
                useCase.addFavorite(people.toFavorite())
            }

            _favoriteState.emit(!favorite)
        }
    }

    private val _favoriteState = MutableStateFlow(false)

    val favoriteState: StateFlow<Boolean>
        get() = _favoriteState.asStateFlow()

}