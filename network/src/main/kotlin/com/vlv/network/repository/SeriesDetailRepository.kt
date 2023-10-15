package com.vlv.network.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.vlv.network.api.SeriesApi
import com.vlv.network.paging.SeriesPagingSource

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