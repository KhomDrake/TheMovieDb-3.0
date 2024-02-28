package com.vlv.people.ui.detail.seriescredit

import androidx.lifecycle.ViewModel
import com.vlv.common.data.tv_show.TvShow
import com.vlv.people.data.repository.PeopleDetailRepository

class SeriesCreditViewModel(private val repository: PeopleDetailRepository) : ViewModel() {

    fun seriesCredit(peopleId: Int) = repository.seriesCredit(peopleId)
        .responseLiveData
        .map {
            it.cast.map(::TvShow)
        }

}