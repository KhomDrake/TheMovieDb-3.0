package com.vlv.configuration.data.api

import com.vlv.configuration.data.model.ConfigurationResponse
import com.vlv.configuration.data.model.CountriesResponseItem
import com.vlv.configuration.data.model.LanguageResponseItem
import com.vlv.configuration.data.model.TimeZonesResponseItem
import retrofit2.http.GET

interface ConfigurationApi {

    @GET("configuration")
    suspend fun configuration() : ConfigurationResponse

    @GET("configuration/countries")
    suspend fun countries() : List<CountriesResponseItem>

    @GET("configuration/languages")
    suspend fun languages() : List<LanguageResponseItem>

    @GET("configuration/countries")
    suspend fun timezones() : List<TimeZonesResponseItem>


}