package com.vlv.series.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.vlv.data.network.paging.SeriesPagingSource
import com.vlv.series.data.api.SeriesApi

class SeriesDetailRepository(private val api: SeriesApi) {

    suspend fun seriesDetail(seriesId: Int) = api.seriesDetail(seriesId)

    suspend fun seriesCast(seriesId: Int) = api.credits(seriesId)

    suspend fun seriesReview(seriesId: Int) = api.reviews(seriesId)

    fun seriesRecommendation(seriesId: Int, config: PagingConfig) = Pager(
        config = config,
        pagingSourceFactory = {
            SeriesPagingSource { page ->
                api.recommendations(
                    seriesId,
                    page
                )
            }
        }
    ).flow

}