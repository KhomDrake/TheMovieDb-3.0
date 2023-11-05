package com.vlv.data.network.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.vlv.data.database.TheMovieDbDao
import com.vlv.data.network.api.MovieApi
import com.vlv.data.network.api.PeopleApi
import com.vlv.data.network.api.SeriesApi
import com.vlv.data.network.model.movie.MovieResponse
import com.vlv.data.network.model.people.PeopleResponse
import com.vlv.data.network.model.series.SeriesItemResponse
import com.vlv.data.network.database.data.History
import com.vlv.data.network.database.data.HistoryType
import com.vlv.data.network.paging.MoviePagingSource
import com.vlv.data.network.paging.PeoplePagingSource
import com.vlv.data.network.paging.SeriesPagingSource
import kotlinx.coroutines.flow.Flow

class SearchRepository(
    private val dao: TheMovieDbDao,
    private val peopleApi: PeopleApi,
    private val movieApi: MovieApi,
    private val seriesApi: SeriesApi
) {

    fun history(type: HistoryType) = dao.historyByType(type)

    suspend fun addHistory(history: History) = dao.insertHistory(history)

    suspend fun deleteHistory(history: History) = dao.deleteHistory(history)

    fun searchMovie(
        pagingConfig: PagingConfig,
        query: String
    ): Flow<PagingData<MovieResponse>> {
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = {
                MoviePagingSource { page ->
                    movieApi.search(query, page)
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
                    peopleApi.search(query, page)
                }
            }
        ).flow
    }

    fun searchSeries(
        pagingConfig: PagingConfig,
        query: String
    ): Flow<PagingData<SeriesItemResponse>> {
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = {
                SeriesPagingSource { page ->
                    seriesApi.search(query, page)
                }
            }
        ).flow
    }

}