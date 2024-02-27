package com.vlv.tv_show.ui.detail.season

import android.content.res.Resources
import androidx.lifecycle.ViewModel
import com.vlv.tv_show.data.repository.TvShowDetailRepository
import com.vlv.tv_show.presentation.model.Season

class SeasonsViewModel(private val repository: TvShowDetailRepository) : ViewModel() {

    fun seriesDetail(resources: Resources, seriesId: Int) = repository
        .tvShowDetail(seriesId)
        .responseLiveData
        .map { response ->
            response.seasons.map { seasonItem ->
                Season(resources, seasonItem)
            }
        }

}