package com.vlv.people.ui.trending

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.map
import com.vlv.common.data.people.People
import com.vlv.network.repository.PeopleRepository
import kotlinx.coroutines.flow.map

class TrendingViewModel(private val repository: PeopleRepository) : ViewModel() {

    private val pagingConfig = PagingConfig(
        pageSize = 20,
        prefetchDistance = 5,
        maxSize = 60,
        initialLoadSize = 20
    )

    fun trending() = repository.trending(
        pagingConfig
    )
        .map {
            it.map { peopleResponse ->
                People(peopleResponse)
            }
        }
        .cachedIn(viewModelScope)

}