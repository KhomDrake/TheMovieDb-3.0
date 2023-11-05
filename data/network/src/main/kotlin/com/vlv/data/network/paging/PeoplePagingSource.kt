package com.vlv.data.network.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.vlv.data.network.model.people.PeopleResponse
import com.vlv.data.network.model.people.PeoplesResponse

class PeoplePagingSource(
    private val api: suspend (Int) -> PeoplesResponse
) : PagingSource<Int, PeopleResponse>() {

    override fun getRefreshKey(state: PagingState<Int, PeopleResponse>) = state.anchorPosition

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PeopleResponse> {
        val page = params.key ?: 1
        return runCatching {
            val response = api.invoke(page)

            val maxPage = if (response.totalPages > MAX_PAGE) MAX_PAGE else response.totalPages

            val nextKey = if (page >= maxPage) null else page + 1
            val prevKey = if(page > 1) page - 1 else null

            LoadResult.Page(response.peopleResponses, prevKey, nextKey)
        }.getOrElse {
            LoadResult.Error(it)
        }
    }

}