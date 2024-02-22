package com.vlv.people.ui.listing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.vlv.common.data.people.People
import com.vlv.common.data.people.PeopleListType
import com.vlv.people.data.repository.PeopleRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class PeopleListingViewModel(
    private val repository: PeopleRepository
) : ViewModel() {

    private val _state: MutableStateFlow<PagingData<People>> =
        MutableStateFlow(PagingData.empty())

    val state: Flow<PagingData<People>>
        get() = _state.asStateFlow()

    private val pagingConfig = PagingConfig(
        pageSize = 20,
        prefetchDistance = 5,
        maxSize = 60,
        initialLoadSize = 20
    )

    fun loadSeriesByType(type: PeopleListType) {
        viewModelScope.launch(Dispatchers.IO) {
            seriesByType(type)
                .map {
                    it.map(::People)
                }
                .cachedIn(viewModelScope)
                .distinctUntilChanged()
                .collectLatest {
                    _state.emit(it)
                }
        }
    }

    private fun seriesByType(type: PeopleListType) = when(type) {
        PeopleListType.TRENDING -> {
            repository.trending(pagingConfig)
        }
        PeopleListType.POPULAR -> {
            repository.popular(pagingConfig)
        }
    }

}