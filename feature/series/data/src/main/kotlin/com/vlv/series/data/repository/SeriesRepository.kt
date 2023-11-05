package com.vlv.series.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.vlv.data.network.model.TimeWindow
import com.vlv.data.network.model.series.SeriesItemResponse
import com.vlv.data.network.model.series.SeriesResponse
import com.vlv.data.network.paging.SeriesPagingSource
import com.vlv.series.data.api.SeriesApi
import kotlinx.coroutines.flow.Flow

class SeriesRepository(private val api: SeriesApi) {

    suspend fun trendingSeries(timeWindow: TimeWindow) : SeriesResponse {
        return api.trending(
            timeWindow.name.lowercase(),
            1
        )
    }

    private fun pagingDefault(
        config: PagingConfig,
        func: suspend (page: Int) -> SeriesResponse
    ) : Flow<PagingData<SeriesItemResponse>> {
        return Pager(
            config = config,
            pagingSourceFactory = {
                SeriesPagingSource { page ->
                    func.invoke(page)
                }
            }
        ).flow
    }

    fun airingTodayPaging(
        config: PagingConfig
    ) = pagingDefault(config) { page ->
        api.airingToday(page)
    }

    fun popularPaging(
        config: PagingConfig
    ) = pagingDefault(config) { page ->
        api.popular(page)
    }

    fun topRatedPaging(
        config: PagingConfig
    ) = pagingDefault(config) { page ->
        api.topRated(page)
    }

    fun onTheAirPaging(
        config: PagingConfig
    ) = pagingDefault(config) { page ->
        api.onTheAir(page)
    }

    fun trendingNowPaging(
        config: PagingConfig,
        timeWindow: TimeWindow
    ) = pagingDefault(config) { page ->
        api.trending(timeWindow.name.lowercase(), page)
    }

    suspend fun airingToday() = api.airingToday(1)

}