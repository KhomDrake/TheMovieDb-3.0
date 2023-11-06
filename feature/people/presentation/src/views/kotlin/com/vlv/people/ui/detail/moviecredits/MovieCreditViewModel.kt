package com.vlv.people.ui.detail.moviecredits

import androidx.lifecycle.ViewModel
import com.vlv.bondsmith.bondsmith
import com.vlv.common.data.movie.Movie
import com.vlv.data.common.model.people.PeopleMovieCreditResponse
import com.vlv.people.data.repository.PeopleDetailRepository

class MovieCreditViewModel(private val repository: PeopleDetailRepository) : ViewModel() {
    fun movieCredit(peopleId: Int) = bondsmith<PeopleMovieCreditResponse>()
        .request {
            repository.movieCredit(peopleId)
        }
        .execute()
        .responseLiveData
        .map {
            it.cast.map(::Movie)
        }

}