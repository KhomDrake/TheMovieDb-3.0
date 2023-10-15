package com.vlv.network.api

import com.vlv.network.data.genre.GenresResponse
import retrofit2.http.GET

interface GenresApi {

    @GET("genre/movie/list")
    suspend fun moviesGenres() : GenresResponse

    @GET("genre/tv/list")
    suspend fun seriesGenres() : GenresResponse

}