package com.vlv.network.repository

import com.vlv.network.api.MovieApi

class MovieDetailRepository(private val api: MovieApi) {

    suspend fun movieDetail(movieId: Int) = api.movieDetail(movieId)

}