package com.vlv.network.api

import com.gustafah.android.mockinterceptor.Mock
import com.vlv.network.data.genre.GenresResponse
import retrofit2.http.GET

interface GenresApi {

    @GET("genre/movie/list")
    @Mock("genres/genres_movies_GET.json")
    suspend fun moviesGenres() : GenresResponse

    @GET("genre/tv/list")
    @Mock("genres/genres_series_GET.json")
    suspend fun seriesGenres() : GenresResponse

}