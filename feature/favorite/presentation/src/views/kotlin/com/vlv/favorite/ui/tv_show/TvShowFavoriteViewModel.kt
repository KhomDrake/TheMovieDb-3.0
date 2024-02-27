package com.vlv.favorite.ui.tv_show

import androidx.lifecycle.ViewModel
import com.vlv.common.data.tv_show.TvShow
import com.vlv.favorite.domain.usecase.TvShowFavoriteUseCase

class TvShowFavoriteViewModel(
    private val useCase: TvShowFavoriteUseCase
) : ViewModel() {

    fun seriesFavorites(shouldTake: Boolean = false) = useCase.favorites()
        .responseLiveData
        .map {
            val items = if(shouldTake) {
                it.take(20)
            } else it
            items.map(::TvShow)
        }

}