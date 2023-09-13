package com.vlv.series.ui.detail.season

import android.content.res.Resources
import androidx.lifecycle.ViewModel
import com.vlv.bondsmith.bondsmith
import com.vlv.network.data.series.SeriesDetailResponse
import com.vlv.network.repository.SeriesDetailRepository

class SeasonsViewModel(private val repository: SeriesDetailRepository) : ViewModel() {

    fun seriesDetail(
        resources: Resources,
        seriesId: Int
    ) = bondsmith<SeriesDetailResponse>()
        .request {
            repository.seriesDetail(seriesId)
        }
        .execute()
        .responseLiveData
        .map {
            it.seasons
        }

}