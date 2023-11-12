package com.vlv.configuration.data.api

import com.vlv.data.common.model.settings.ConfigurationResponse
import com.vlv.data.common.model.settings.CountriesResponseItem
import com.vlv.data.common.model.settings.LanguageResponseItem
import com.vlv.data.common.model.settings.TimeZonesResponseItem
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