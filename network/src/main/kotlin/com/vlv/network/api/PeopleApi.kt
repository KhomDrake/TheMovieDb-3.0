package com.vlv.network.api

import com.vlv.network.data.people.PeopleDetailResponse
import com.vlv.network.data.people.PeopleMovieCreditResponse
import com.vlv.network.data.people.PeopleSeriesCreditResponse
import com.vlv.network.data.people.PeoplesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PeopleApi {

    @GET("person/popular")
    suspend fun popularPeople(
        @Query("language")
        language: String = "en-US",
        @Query("page")
        page: Int = 1
    ) : PeoplesResponse

    @GET("trending/person")
    suspend fun trendingPeople(
        @Query("language")
        language: String = "en-US",
        @Query("page")
        page: Int = 1
    ) : PeoplesResponse

    @GET("people/{people_id}")
    suspend fun peopleDetail(
        @Path("people_id")
        peopleId: Int
    ) : PeopleDetailResponse


    @GET("people/{people_id}/movie_credits")
    suspend fun peopleDetail2(
        @Path("people_id")
        peopleId: Int,
        @Query("language")
        language: String = "en-US",
        @Query("page")
        page: Int = 1
    ) : PeopleMovieCreditResponse


    @GET("people/{people_id}/tv_credits")
    suspend fun peopleDetail1(
        @Path("people_id")
        peopleId: Int,
        @Query("language")
        language: String = "en-US",
        @Query("page")
        page: Int = 1
    ) : PeopleSeriesCreditResponse

}