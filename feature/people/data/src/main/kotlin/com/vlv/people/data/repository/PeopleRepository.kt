package com.vlv.people.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.vlv.data.common.model.TimeWindow
import com.vlv.data.common.model.people.PeopleResponse
import com.vlv.data.common.model.people.PeoplesResponse
import com.vlv.data.common.paging.PeoplePagingSource
import com.vlv.people.data.api.PeopleApi
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