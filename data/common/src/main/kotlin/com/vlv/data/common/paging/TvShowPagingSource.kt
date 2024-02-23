package com.vlv.data.common.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.vlv.data.common.model.tvshow.TvShowResponse
import com.vlv.data.common.model.tvshow.TvShowsResponse

class TvShowPagingSource(
    private val api: suspend (Int) -> TvShowsResponse
) : PagingSource<Int, TvShowResponse>() {

    override fun getRefreshKey(state: PagingState<Int, TvShowResponse>) = state.anchorPosition

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TvShowResponse> {
        val page = params.key ?: 1
        return runCatching {
            val response = api.invoke(page)

            val maxPage = if (response.totalPages > MAX_PAGE) MAX_PAGE else response.totalPages

            val nextKey = if (page >= maxPage) null else page + 1
            val prevKey = if(page > 1) page - 1 else null

            LoadResult.Page(response.tvShows, prevKey, nextKey)
        }.getOrElse {
            LoadResult.Error(it)
        }
    }

}