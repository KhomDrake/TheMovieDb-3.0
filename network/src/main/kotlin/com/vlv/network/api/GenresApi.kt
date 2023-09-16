package com.vlv.network.api

import com.vlv.network.data.genre.GenresResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GenresApi {

    @GET("genre/movie/list")
    suspend fun moviesGenres(
        @Query("language")
        language: String = "en-US"
    ) : GenresResponse

    @GET("genre/tv/list")
    suspend fun seriesGenres(
        @Query("language")
        language: String = "en-US"
    ) : GenresResponse

}