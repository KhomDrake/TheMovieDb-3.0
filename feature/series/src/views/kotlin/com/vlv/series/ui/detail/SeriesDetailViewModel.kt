package com.vlv.series.ui.detail

import androidx.lifecycle.ViewModel
import com.vlv.bondsmith.bondsmith
import com.vlv.common.data.series.Series
import com.vlv.common.data.series.toFavorite
import com.vlv.network.repository.FavoriteRepository

class SeriesDetailViewModel(private val repository: FavoriteRepository) : ViewModel() {

    fun changeFavorite(series: Series) = bondsmith<Boolean>()
        .request {
            val favorite = repository.getFavorite(series.id)
            if(favorite != null) {
                repository.removeFavorite(favorite)
                false
            } else {
                repository.addFavorite(series.toFavorite())
                true
            }

        }
        .execute()
        .responseLiveData

    fun getFavorite(seriesId: Int) = bondsmith<Boolean>()
        .request {
            repository.getFavorite(seriesId) != null
        }
        .execute()
        .responseLiveData


}