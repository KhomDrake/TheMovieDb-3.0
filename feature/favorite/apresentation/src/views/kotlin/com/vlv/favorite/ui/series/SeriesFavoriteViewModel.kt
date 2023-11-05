package com.vlv.favorite.ui.series

import androidx.lifecycle.ViewModel
import com.vlv.bondsmith.bondsmith
import com.vlv.common.data.series.Series
import com.vlv.data.network.database.data.Favorite
import com.vlv.favorite.domain.usecase.SeriesFavoriteUseCase

class SeriesFavoriteViewModel(
    private val useCase: SeriesFavoriteUseCase
) : ViewModel() {

    fun seriesFavorites(shouldTake: Boolean = false) = bondsmith<List<Favorite>>()
        .request {
            val data = useCase.favorites()
            if(shouldTake) data.take(20) else data
        }
        .execute()
        .responseLiveData
        .map {
            it.map(::Series)
        }

}