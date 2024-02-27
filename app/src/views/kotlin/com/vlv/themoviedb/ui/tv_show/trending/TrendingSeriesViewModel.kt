package com.vlv.themoviedb.ui.tv_show.trending

import androidx.lifecycle.ViewModel
import com.vlv.common.data.tv_show.TvShow
import com.vlv.data.common.model.TimeWindow
import com.vlv.tv_show.data.repository.TvShowRepository

class TrendingSeriesViewModel(private val repository: TvShowRepository) : ViewModel() {

    fun trendingNow() = repository.tvShowsTrending(TimeWindow.DAY)
        .responseLiveData
        .map { it.tvShows.map(::TvShow) }

}