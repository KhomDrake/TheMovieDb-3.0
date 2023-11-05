package com.vlv.data.network.model.people

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class PeopleMovieCreditResponse(
    val cast: List<MovieCreditResponse>
)

@JsonClass(generateAdapter = true)
class PeopleSeriesCreditResponse(
    val cast: List<SeriesCreditResponse>
)

