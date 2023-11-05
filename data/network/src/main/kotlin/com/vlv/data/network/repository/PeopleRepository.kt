package com.vlv.data.network.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.vlv.data.network.api.PeopleApi
import com.vlv.data.network.model.people.PeopleResponse
import com.vlv.data.network.model.people.PeoplesResponse
import com.vlv.data.network.paging.PeoplePagingSource
import kotlinx.coroutines.flow.Flow

class PeopleRepository(private val peopleApi: PeopleApi) {

    private fun pagingDefault(
        config: PagingConfig,
        func: suspend (page: Int) -> PeoplesResponse
    ): Flow<PagingData<PeopleResponse>> {
        return Pager(
            config = config,
            pagingSourceFactory = {
                PeoplePagingSource { page ->
                    func.invoke(page)
                }
            }
        ).flow
    }

    fun popular(config: PagingConfig) = pagingDefault(config) { page ->
        peopleApi.popularPeople(page = page)
    }

    fun trending(config: PagingConfig) = pagingDefault(config) { page ->
        peopleApi.trendingPeople(
            timeWindow = TimeWindow.DAY.name.lowercase(),
            page = page
        )
    }

}