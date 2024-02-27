package com.vlv.tv_show.ui.detail.cast

import androidx.lifecycle.ViewModel
import com.vlv.common.data.cast.Cast
import com.vlv.tv_show.data.repository.TvShowDetailRepository

class SeriesCastViewModel(private val repository: TvShowDetailRepository) : ViewModel() {

    fun cast(seriesId: Int) = repository
        .tvShowCast(seriesId)
        .responseLiveData
        .map {
            it.castResponse.map(::Cast)
        }


}