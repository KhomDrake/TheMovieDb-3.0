package com.vlv.series.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.vlv.bondsmith.bondsmith
import com.vlv.data.common.model.credit.CreditsResponse
import com.vlv.data.common.model.review.ReviewsResponse
import com.vlv.data.common.model.series.SeriesDetailResponse
import com.vlv.data.common.paging.SeriesPagingSource
import com.vlv.series.data.api.SeriesApi

class SeriesDetailRepository(private val api: SeriesApi) {

    suspend fun seriesDetail(seriesId: Int) = bondsmith<SeriesDetailResponse>(
        "SERIES-DETAIL-$seriesId"
    )
        .config {
            request {
                api.seriesDetail(seriesId)
            }
        }
        .execute()


    suspend fun seriesCast(seriesId: Int) = bondsmith<CreditsResponse>(
        "SERIES-CAST-$seriesId"
    )
        .config {
            request {
                api.credits(seriesId)
            }
        }
        .execute()


    suspend fun seriesReview(seriesId: Int) = bondsmith<ReviewsResponse>(
        "SERIES-REVIEWS-$seriesId"
    )
        .config {
            request {
                api.reviews(seriesId)
            }
        }
        .execute()

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