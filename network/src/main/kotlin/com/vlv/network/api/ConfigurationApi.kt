package com.vlv.network.api

import com.gustafah.android.mockinterceptor.Mock
import com.vlv.network.data.configuration.ConfigurationResponse
import com.vlv.network.data.configuration.CountriesResponseItem
import com.vlv.network.data.configuration.LanguageResponseItem
import com.vlv.network.data.configuration.TimeZonesResponseItem
import retrofit2.http.GET
import retrofit2.http.Query

interface ConfigurationApi {

    @GET("configuration")
    @Mock("configuration/config_GET.json")
    suspend fun configuration() : ConfigurationResponse

    @GET("configuration/countries")
    @Mock("configuration/countries_GET.json")
    suspend fun countries(
        @Query("language")
        language: String = "en-US"
    ) : List<CountriesResponseItem>

    @GET("configuration/languages")
    @Mock("configuration/languages_GET.json")
    suspend fun languages() : List<LanguageResponseItem>

    @GET("configuration/countries")
    suspend fun timezones() : List<TimeZonesResponseItem>


}