package com.vlv.people.ui.detail.about

import androidx.lifecycle.ViewModel
import com.vlv.people.data.repository.PeopleDetailRepository
import com.vlv.people.ui.data.PeopleDetail

class AboutViewModel(
    private val repository: PeopleDetailRepository
) : ViewModel() {

    fun peopleDetail(id: Int) = repository
        .peopleDetail(id)
        .responseLiveData
        .map { detail ->
            PeopleDetail(detail).about
        }
}