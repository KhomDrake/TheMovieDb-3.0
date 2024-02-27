package com.vlv.tv_show.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.vlv.bondsmith.bondsmith
import com.vlv.data.common.model.credit.CreditsResponse
import com.vlv.data.common.model.review.ReviewsResponse
import com.vlv.data.common.model.tvshow.TvShowDetailResponse
import com.vlv.data.common.paging.TvShowPagingSource
import com.vlv.tv_show.data.api.TvShowApi

class TvShowDetailRepository(private val api: TvShowApi) {

    fun tvShowDetail(seriesId: Int) = bondsmith<TvShowDetailResponse>(
        "TV-SHOW-DETAIL-$seriesId"
    )
        .config {
            request {
                api.tvShowDetail(seriesId)
            }
        }
        .execute()


    fun tvShowCast(seriesId: Int) = bondsmith<CreditsResponse>(
        "TV-SHOW-CAST-$seriesId"
    )
        .config {
            request {
                api.credits(seriesId)
            }
        }
        .execute()


    fun tvShowReview(seriesId: Int) = bondsmith<ReviewsResponse>(
        "TV-SHOW-REVIEWS-$seriesId"
    )
        .config {
            request {
                api.reviews(seriesId)
            }
        }
        .execute()

    fun tvShowRecommendation(seriesId: Int, config: PagingConfig) = Pager(
        config = config,
        pagingSourceFactory = {
            TvShowPagingSource { page ->
                api.recommendations(
                    seriesId,
                    page
                )
            }
        }
    ).flow

}