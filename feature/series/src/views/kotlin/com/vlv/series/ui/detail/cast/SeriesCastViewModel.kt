package com.vlv.series.ui.detail.cast

import androidx.lifecycle.ViewModel
import com.vlv.bondsmith.bondsmith
import com.vlv.common.data.cast.Cast
import com.vlv.network.data.credit.CreditsResponse
import com.vlv.network.repository.SeriesDetailRepository

class SeriesCastViewModel(private val repository: SeriesDetailRepository) : ViewModel() {

    fun cast(seriesId: Int) = bondsmith<CreditsResponse>()
        .request {
            repository.seriesCast(seriesId)
        }
        .execute()
        .responseLiveData
        .map {
            it.castResponse.map(::Cast)
        }


}