package com.vlv.themoviedb.ui.series.trending

import androidx.lifecycle.ViewModel
import com.vlv.bondsmith.bondsmith
import com.vlv.network.data.series.SeriesResponse
import com.vlv.network.repository.SeriesRepository
import com.vlv.network.repository.TimeWindow
import com.vlv.series.data.Series

class TrendingSeriesViewModel(private val repository: SeriesRepository) : ViewModel() {

    fun trendingNow() = bondsmith<SeriesResponse>()
        .request {
            repository.trendingSeries(TimeWindow.DAY)
        }
        .execute()
        .responseLiveData
        .map { it.series.map(::Series) }

}