package com.vlv.network.repository

import com.vlv.network.api.SeriesApi

class SeriesDetailRepository(private val api: SeriesApi) {

    suspend fun seriesDetail(seriesId: Int) = api.seriesDetail(seriesId, "en-US")

    suspend fun seriesCast(seriesId: Int) = api.credits(seriesId, "en-US")

    suspend fun seriesReview(seriesId: Int) = api.reviews(seriesId, "en-US")

}