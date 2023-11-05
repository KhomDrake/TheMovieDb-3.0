package com.vlv.data.network.api

import com.vlv.data.network.model.people.PeopleDetailResponse
import com.vlv.data.network.model.people.PeopleMovieCreditResponse
import com.vlv.data.network.model.people.PeopleSeriesCreditResponse
import com.vlv.data.network.model.people.PeoplesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PeopleApi {

    @GET("person/popular")
    suspend fun popularPeople(
        @Query("page")
        page: Int = 1
    ) : PeoplesResponse

    @GET("trending/person/{time_window}")
    suspend fun trendingPeople(
        @Path("time_window")
        timeWindow: String,
        @Query("page")
        page: Int = 1
    ) : PeoplesResponse

    @GET("person/{people_id}")
    suspend fun peopleDetail(
        @Path("people_id")
        peopleId: Int
    ) : PeopleDetailResponse

    @GET("search/person")
    suspend fun search(
        @Query("query")
        query: String,
        @Query("page")
        page: Int = 1
    ) : PeoplesResponse


    @GET("person/{people_id}/movie_credits")
    suspend fun peopleMovieCredits(
        @Path("people_id")
        peopleId: Int,
        @Query("page")
        page: Int = 1
    ) : PeopleMovieCreditResponse


    @GET("person/{people_id}/tv_credits")
    suspend fun peopleTvCredits(
        @Path("people_id")
        peopleId: Int,
        @Query("page")
        page: Int = 1
    ) : PeopleSeriesCreditResponse

}