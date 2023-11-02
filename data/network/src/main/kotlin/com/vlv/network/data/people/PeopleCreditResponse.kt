package com.vlv.network.data.people

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class PeopleMovieCreditResponse(
    val cast: List<MovieCreditResponse>
)

@JsonClass(generateAdapter = true)
class PeopleSeriesCreditResponse(
    val cast: List<SeriesCreditResponse>
)

