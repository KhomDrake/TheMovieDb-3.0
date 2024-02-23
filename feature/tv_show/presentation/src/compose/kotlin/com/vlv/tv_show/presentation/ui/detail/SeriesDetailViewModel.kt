package com.vlv.tv_show.presentation.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vlv.common.data.movie.toFavorite
import com.vlv.common.data.movie.toMovie
import com.vlv.common.ui.DetailObject
import com.vlv.favorite.domain.usecase.FavoriteUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class SeriesDetailViewModel(private val favoriteUseCase: FavoriteUseCase) : ViewModel() {

    val favoriteState = MutableStateFlow<Boolean>(false)

    fun isFavorite(detailObject: DetailObject) {
        viewModelScope.launch(Dispatchers.IO) {
            val isFavorite = favoriteUseCase.getFavorite(detailObject.id) != null
            favoriteState.emit(isFavorite)
        }
    }

    fun changeFavorite(detailObject: DetailObject, remove: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            val favorite = detailObject.toMovie().toFavorite()
            if(remove) {
                favoriteUseCase.removeFavorite(favorite)
            } else {
                favoriteUseCase.addFavorite(favorite)
            }

            favoriteState.emit(!remove)
        }
    }

}