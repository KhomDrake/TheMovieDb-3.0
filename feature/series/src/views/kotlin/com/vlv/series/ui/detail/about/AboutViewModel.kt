package com.vlv.series.ui.detail.about

import android.content.res.Resources
import androidx.lifecycle.ViewModel
import com.vlv.bondsmith.bondsmith
import com.vlv.network.data.series.SeriesDetailResponse
import com.vlv.network.repository.SeriesDetailRepository
import com.vlv.series.data.SeriesDetail

class AboutViewModel(private val repository: SeriesDetailRepository) : ViewModel() {

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
            SeriesDetail(resources, it)
        }
}