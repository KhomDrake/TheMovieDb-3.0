package com.vlv.genre.data.api

import com.vlv.data.common.model.genre.GenresResponse
import retrofit2.http.GET

interface GenresApi {

    @GET("genre/movie/list")
    suspend fun moviesGenres() : GenresResponse

    @GET("genre/tv/list")
    suspend fun tvShowGenres() : GenresResponse

}