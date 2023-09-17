package com.vlv.themoviedb.ui.series.airingtoday

import androidx.lifecycle.ViewModel
import com.vlv.bondsmith.bondsmith
import com.vlv.common.data.series.Series
import com.vlv.network.data.series.SeriesResponse
import com.vlv.network.repository.SeriesRepository

class AiringTodayViewModel(private val repository: SeriesRepository) : ViewModel() {

    fun airingToday() = bondsmith<SeriesResponse>()
        .request {
            repository.airingToday()
        }
        .execute()
        .responseLiveData
        .map { it.series.map(::Series) }

}