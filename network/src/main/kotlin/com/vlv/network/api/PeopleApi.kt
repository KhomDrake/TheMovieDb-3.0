package com.vlv.network.api

import com.gustafah.android.mockinterceptor.Mock
import com.vlv.network.data.people.PeopleDetailResponse
import com.vlv.network.data.people.PeopleMovieCreditResponse
import com.vlv.network.data.people.PeopleSeriesCreditResponse
import com.vlv.network.data.people.PeoplesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PeopleApi {

    @GET("person/popular")
    @Mock("people/popular_GET.json")
    suspend fun popularPeople(
        @Query("page")
        page: Int = 1
    ) : PeoplesResponse

    @GET("trending/person/{time_window}")
    @Mock("people/trending_GET.json")
    suspend fun trendingPeople(
        @Path("time_window")
        timeWindow: String,
        @Query("page")
        page: Int = 1
    ) : PeoplesResponse

    @GET("person/{people_id}")
    @Mock("people/detail_GET.json")
    suspend fun peopleDetail(
        @Path("people_id")
        peopleId: Int
    ) : PeopleDetailResponse

    @GET("search/person")
    @Mock("people/search_GET.json")
    suspend fun search(
        @Query("query")
        query: String,
        @Query("page")
        page: Int = 1
    ) : PeoplesResponse


    @GET("person/{people_id}/movie_credits")
    @Mock("people/movie_credits_GET.json")
    suspend fun peopleMovieCredits(
        @Path("people_id")
        peopleId: Int,
        @Query("page")
        page: Int = 1
    ) : PeopleMovieCreditResponse


    @GET("person/{people_id}/tv_credits")
    @Mock("people/series_credits_GET.json")
    suspend fun peopleTvCredits(
        @Path("people_id")
        peopleId: Int,
        @Query("page")
        page: Int = 1
    ) : PeopleSeriesCreditResponse

}