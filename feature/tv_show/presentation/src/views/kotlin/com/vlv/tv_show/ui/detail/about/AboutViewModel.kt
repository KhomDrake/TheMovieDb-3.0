package com.vlv.tv_show.ui.detail.about

import android.content.res.Resources
import androidx.lifecycle.ViewModel
import com.vlv.tv_show.data.repository.TvShowDetailRepository
import com.vlv.tv_show.presentation.model.TvShowDetail

class AboutViewModel(private val repository: TvShowDetailRepository) : ViewModel() {

    fun seriesDetail(
        resources: Resources,
        seriesId: Int
    ) = repository.tvShowDetail(seriesId)
        .responseLiveData
        .map {
            TvShowDetail(resources, it)
        }
}