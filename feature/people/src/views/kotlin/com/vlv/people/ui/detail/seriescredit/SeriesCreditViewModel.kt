package com.vlv.people.ui.detail.seriescredit

import androidx.lifecycle.ViewModel
import com.vlv.bondsmith.bondsmith
import com.vlv.common.data.series.Series
import com.vlv.network.repository.PeopleDetailRepository

class SeriesCreditViewModel(private val repository: PeopleDetailRepository) : ViewModel() {

    fun seriesCredit(peopleId: Int) = bondsmith<List<Series>>()
        .request {
            repository.seriesCredit(peopleId).cast.map(::Series)
        }
        .execute()
        .responseLiveData

}