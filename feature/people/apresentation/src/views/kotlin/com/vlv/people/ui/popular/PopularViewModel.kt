package com.vlv.people.ui.popular

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.map
import com.vlv.common.data.people.People
import com.vlv.people.data.repository.PeopleRepository
import kotlinx.coroutines.flow.map

class PopularViewModel(
    private val repository: PeopleRepository
) : ViewModel() {

    private val pagingConfig = PagingConfig(
        pageSize = 20,
        prefetchDistance = 5,
        maxSize = 60,
        initialLoadSize = 20
    )

    fun popular() = repository.popular(
        pagingConfig
    )
        .map {
            it.map { peopleResponse ->
                People(peopleResponse)
            }
        }
        .cachedIn(viewModelScope)

}