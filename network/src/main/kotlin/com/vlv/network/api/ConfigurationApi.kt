package com.vlv.network.api

import com.vlv.network.data.configuration.ConfigurationResponse
import com.vlv.network.data.configuration.CountriesResponseItem
import com.vlv.network.data.configuration.LanguageResponseItem
import com.vlv.network.data.configuration.TimeZonesResponseItem
import retrofit2.http.GET
import retrofit2.http.Query

interface ConfigurationApi {

    @GET("configuration")
    suspend fun configuration(
        @Query("language")
        language: String = "en-US"
    ) : ConfigurationResponse

    @GET("configuration/countries")
    suspend fun countries(
        @Query("language")
        language: String = "en-US"
    ) : List<CountriesResponseItem>

    @GET("configuration/languages")
    suspend fun languages(
        @Query("language")
        language: String = "en-US"
    ) : List<LanguageResponseItem>

    @GET("configuration/countries")
    suspend fun timezones(
        @Query("language")
        language: String = "en-US"
    ) : List<TimeZonesResponseItem>


}