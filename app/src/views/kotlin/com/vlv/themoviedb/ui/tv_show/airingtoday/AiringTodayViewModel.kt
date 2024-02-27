package com.vlv.themoviedb.ui.tv_show.airingtoday

import androidx.lifecycle.ViewModel
import com.vlv.common.data.tv_show.TvShow
import com.vlv.tv_show.data.repository.TvShowRepository

class AiringTodayViewModel(private val repository: TvShowRepository) : ViewModel() {

    fun airingToday() = repository
        .airingToday()
        .responseLiveData
        .map { it.tvShows.map(::TvShow) }

}