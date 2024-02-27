package com.vlv.tv_show.ui.detail

import androidx.lifecycle.ViewModel
import com.vlv.bondsmith.bondsmith
import com.vlv.common.data.tv_show.TvShow
import com.vlv.common.data.tv_show.toFavorite
import com.vlv.favorite.domain.usecase.FavoriteUseCase

class SeriesDetailViewModel(private val useCase: FavoriteUseCase) : ViewModel() {

    fun changeFavorite(series: TvShow) = bondsmith<Boolean>()
        .config {
            request {
                val favorite = useCase.getFavorite(series.id)
                if(favorite != null) {
                    useCase.removeFavorite(favorite)
                    false
                } else {
                    useCase.addFavorite(series.toFavorite())
                    true
                }
            }
        }
        .execute()
        .responseLiveData

    fun getFavorite(seriesId: Int) = bondsmith<Boolean>()
        .config {
            request {
                useCase.getFavorite(seriesId) != null
            }
        }
        .execute()
        .responseLiveData


}