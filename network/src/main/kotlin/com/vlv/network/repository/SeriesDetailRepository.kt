package com.vlv.network.repository

import com.vlv.network.api.SeriesApi

class SeriesDetailRepository(private val api: SeriesApi) {

    suspend fun seriesDetail(seriesId: Int) = api.seriesDetail(seriesId, "en-US")

}