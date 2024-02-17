package com.vlv.people.ui.tab

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vlv.bondsmith.data.flow.MutableResponseStateFlow
import com.vlv.bondsmith.data.flow.ResponseStateFlow
import com.vlv.bondsmith.data.flow.asResponseStateFlow
import com.vlv.common.data.movie.Movie
import com.vlv.common.data.people.People
import com.vlv.common.data.series.Series
import com.vlv.people.data.repository.PeopleDetailRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PeopleCreditViewModel(
    private val repository: PeopleDetailRepository
) : ViewModel() {

    private val _movieState = MutableResponseStateFlow<List<Movie>>()

    val movieCreditState: ResponseStateFlow<List<Movie>>
        get() = _movieState.asResponseStateFlow()

    private val _seriesState = MutableResponseStateFlow<List<Series>>()

    val seriesCreditState: ResponseStateFlow<List<Series>>
        get() = _seriesState.asResponseStateFlow()

    fun moviesCredit(people: People) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.movieCredit(people.id)
                .responseStateFlow
                .mapData {
                    it?.cast?.map(::Movie) ?: listOf()
                }
                .collectLatest {
                    _movieState.emit(it)
                }
        }
    }

    fun seriesCredit(people: People) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.seriesCredit(people.id)
                .responseStateFlow
                .mapData {
                    it?.cast?.map(::Series) ?: listOf()
                }
                .collectLatest {
                    _seriesState.emit(it)
                }
        }
    }

}