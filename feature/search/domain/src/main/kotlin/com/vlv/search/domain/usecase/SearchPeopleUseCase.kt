package com.vlv.search.domain.usecase

import androidx.paging.PagingConfig
import com.vlv.search.data.repository.SearchRepository

class SearchPeopleUseCase(
    private val repository: SearchRepository
) {
    fun searchPeople(pagingConfig: PagingConfig, query: String) =
        repository.searchPeople(pagingConfig, query)
}