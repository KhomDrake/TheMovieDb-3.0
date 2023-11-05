package com.vlv.data.network.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.vlv.data.network.model.movie.MovieResponse
import com.vlv.data.network.model.movie.MoviesResponse

const val MAX_PAGE = 500

class MoviePagingSource(
    private val api: suspend (Int) -> MoviesResponse
) : PagingSource<Int, MovieResponse>() {

    override fun getRefreshKey(state: PagingState<Int, MovieResponse>) = state.anchorPosition

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieResponse> {
        val page = params.key ?: 1
        return runCatching {
            val response = api.invoke(page)

            val maxPage = if (response.totalPages > MAX_PAGE) MAX_PAGE else response.totalPages

            val nextKey = if (page >= maxPage) null else page + 1
            val prevKey = if(page > 1) page - 1 else null

            LoadResult.Page(response.movies, prevKey, nextKey)
        }.getOrElse {
            LoadResult.Error(it)
        }
    }

}