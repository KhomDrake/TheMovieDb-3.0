package com.vlv.search.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.vlv.data.common.model.movie.MovieResponse
import com.vlv.data.common.model.people.PeopleResponse
import com.vlv.data.common.model.tvshow.TvShowResponse
import com.vlv.data.common.paging.MoviePagingSource
import com.vlv.data.common.paging.PeoplePagingSource
import com.vlv.data.common.paging.TvShowPagingSource
import com.vlv.search.data.api.SearchApi
import kotlinx.coroutines.flow.Flow

class SearchRepository(
    private val searchApi: SearchApi
) {

    fun searchMovie(
        pagingConfig: PagingConfig,
        query: String
    ): Flow<PagingData<MovieResponse>> {
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = {
                MoviePagingSource { page ->
                    searchApi.searchMovie(query, page)
                }
            }
        ).flow
    }

    fun searchPeople(
        pagingConfig: PagingConfig,
        query: String
    ): Flow<PagingData<PeopleResponse>> {
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = {
                PeoplePagingSource { page ->
                    searchApi.searchPeople(query, page)
                }
            }
        ).flow
    }

    fun searchTvShows(
        pagingConfig: PagingConfig,
        query: String
    ): Flow<PagingData<TvShowResponse>> {
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = {
                TvShowPagingSource { page ->
                    searchApi.searchTvShows(query, page)
                }
            }
        ).flow
    }

}