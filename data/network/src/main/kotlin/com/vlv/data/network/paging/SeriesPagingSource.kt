package com.vlv.data.network.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.vlv.data.network.model.series.SeriesItemResponse
import com.vlv.data.network.model.series.SeriesResponse

class SeriesPagingSource(
    private val api: suspend (Int) -> SeriesResponse
) : PagingSource<Int, SeriesItemResponse>() {

    override fun getRefreshKey(state: PagingState<Int, SeriesItemResponse>) = state.anchorPosition

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SeriesItemResponse> {
        val page = params.key ?: 1
        return runCatching {
            val response = api.invoke(page)

            val maxPage = if (response.totalPages > MAX_PAGE) MAX_PAGE else response.totalPages

            val nextKey = if (page >= maxPage) null else page + 1
            val prevKey = if(page > 1) page - 1 else null

            LoadResult.Page(response.series, prevKey, nextKey)
        }.getOrElse {
            LoadResult.Error(it)
        }
    }

}