package com.vlv.series.ui.detail.about

import android.content.res.Resources
import androidx.lifecycle.ViewModel
import com.vlv.bondsmith.bondsmith
import com.vlv.data.common.model.series.SeriesDetailResponse
import com.vlv.series.data.SeriesDetail
import com.vlv.series.data.repository.SeriesDetailRepository

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