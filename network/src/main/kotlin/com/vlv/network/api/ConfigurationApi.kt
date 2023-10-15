package com.vlv.network.api

import com.vlv.network.data.configuration.ConfigurationResponse
import com.vlv.network.data.configuration.CountriesResponseItem
import com.vlv.network.data.configuration.LanguageResponseItem
import com.vlv.network.data.configuration.TimeZonesResponseItem
import retrofit2.http.GET
import retrofit2.http.Query

interface ConfigurationApi {

    @GET("configuration")
    suspend fun configuration() : ConfigurationResponse

    @GET("configuration/countries")
    suspend fun countries(
        @Query("language")
        language: String = "en-US"
    ) : List<CountriesResponseItem>

    @GET("configuration/languages")
    suspend fun languages() : List<LanguageResponseItem>

    @GET("configuration/countries")
    suspend fun timezones() : List<TimeZonesResponseItem>


}