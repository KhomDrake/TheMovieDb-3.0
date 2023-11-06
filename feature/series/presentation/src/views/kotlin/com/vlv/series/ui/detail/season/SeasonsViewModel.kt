package com.vlv.series.ui.detail.season

import androidx.lifecycle.ViewModel
import com.vlv.bondsmith.bondsmith
import com.vlv.data.common.model.series.SeriesDetailResponse
import com.vlv.series.data.repository.SeriesDetailRepository

class SeasonsViewModel(private val repository: SeriesDetailRepository) : ViewModel() {

    fun seriesDetail(seriesId: Int) = bondsmith<SeriesDetailResponse>()
        .request {
            repository.seriesDetail(seriesId)
        }
        .execute()
        .responseLiveData
        .map {
            it.seasons
        }

}