package com.vlv.tv_show.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.vlv.bondsmith.bondsmith
import com.vlv.data.common.model.TimeWindow
import com.vlv.data.common.model.tvshow.TvShowResponse
import com.vlv.data.common.model.tvshow.TvShowsResponse
import com.vlv.data.common.paging.TvShowPagingSource
import com.vlv.tv_show.data.api.TvShowApi
import kotlinx.coroutines.flow.Flow

class TvShowRepository(private val api: TvShowApi) {

    fun tvShowsTrending(timeWindow: TimeWindow) = bondsmith<TvShowsResponse>(
        "TV-SHOW-TRENDING-${timeWindow.name.lowercase()}"
    )
        .config {
            request {
                api.trending(
                    timeWindow.name.lowercase(),
                    1
                )
            }
        }
        .execute()

    private fun pagingDefault(
        config: PagingConfig,
        func: suspend (page: Int) -> TvShowsResponse
    ) : Flow<PagingData<TvShowResponse>> {
        return Pager(
            config = config,
            pagingSourceFactory = {
                TvShowPagingSource { page ->
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

    fun airingToday() = bondsmith<TvShowsResponse>(
        "TV-SHOW-AIRING-TODAY"
    )
        .config {
            request {
                api.airingToday(1)
            }
        }
        .execute()

}