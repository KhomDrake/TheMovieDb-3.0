package com.vlv.people.ui.detail.moviecredits

import androidx.lifecycle.ViewModel
import com.vlv.common.data.movie.Movie
import com.vlv.people.data.repository.PeopleDetailRepository

class MovieCreditViewModel(private val repository: PeopleDetailRepository) : ViewModel() {
    fun movieCredit(peopleId: Int) =
        repository.movieCredit(peopleId)
        .responseLiveData
        .map {
            it.cast.map(::Movie)
        }

}