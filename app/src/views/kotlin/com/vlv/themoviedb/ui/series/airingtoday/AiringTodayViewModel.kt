package com.vlv.themoviedb.ui.series.airingtoday

import androidx.lifecycle.ViewModel
import com.vlv.bondsmith.bondsmith
import com.vlv.common.data.series.Series
import com.vlv.data.common.model.series.SeriesResponse
import com.vlv.series.data.repository.SeriesRepository

class AiringTodayViewModel(private val repository: SeriesRepository) : ViewModel() {

    fun airingToday() = bondsmith<SeriesResponse>()
        .request {
            repository.airingToday()
        }
        .execute()
        .responseLiveData
        .map { it.series.map(::Series) }

}