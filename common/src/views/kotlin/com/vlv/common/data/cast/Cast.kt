package com.vlv.common.data.cast

import com.vlv.network.data.credit.CastResponse

class Cast(
    val castId: Int,
    val character: String,
    val personId: Int,
    val name: String,
    val originalName: String,
    val profilePath: String?
) {
    constructor(castResponse: CastResponse) : this(
        castResponse.castId ?: castResponse.id,
        castResponse.character,
        castResponse.id,
        castResponse.name,
        castResponse.originalName,
        castResponse.profilePath,
    )
}