package com.vlv.themoviedb.ui.series.trending

import androidx.lifecycle.ViewModel
import com.vlv.bondsmith.bondsmith
import com.vlv.common.data.series.Series
import com.vlv.data.network.model.TimeWindow
import com.vlv.data.network.model.series.SeriesResponse
import com.vlv.series.data.repository.SeriesRepository

class TrendingSeriesViewModel(private val repository: SeriesRepository) : ViewModel() {

    fun trendingNow() = bondsmith<SeriesResponse>()
        .request {
            repository.trendingSeries(TimeWindow.DAY)
        }
        .execute()
        .responseLiveData
        .map { it.series.map(::Series) }

}