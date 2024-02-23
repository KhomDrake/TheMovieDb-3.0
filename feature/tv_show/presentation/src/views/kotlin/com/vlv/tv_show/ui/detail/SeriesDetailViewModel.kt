package com.vlv.series.ui.detail

import androidx.lifecycle.ViewModel
import com.vlv.bondsmith.bondsmith
import com.vlv.common.data.series.Series
import com.vlv.common.data.series.toFavorite
import com.vlv.favorite.domain.usecase.FavoriteUseCase

class SeriesDetailViewModel(private val useCase: FavoriteUseCase) : ViewModel() {

    fun changeFavorite(series: Series) = bondsmith<Boolean>()
        .request {
            val favorite = useCase.getFavorite(series.id)
            if(favorite != null) {
                useCase.removeFavorite(favorite)
                false
            } else {
                useCase.addFavorite(series.toFavorite())
                true
            }

        }
        .execute()
        .responseLiveData

    fun getFavorite(seriesId: Int) = bondsmith<Boolean>()
        .request {
            useCase.getFavorite(seriesId) != null
        }
        .execute()
        .responseLiveData


}