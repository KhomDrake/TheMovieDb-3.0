package com.vlv.configuration.data.model

import com.vlv.data.database.data.CountryEntity
import com.vlv.data.database.data.ImageEntity
import com.vlv.data.database.data.LanguageEntity

class ConfigurationData(
    val backdropSizes: List<ImageEntity>,
    val posterSizes: List<ImageEntity>,
    val profileSizes: List<ImageEntity>,
    val logoSizes: List<ImageEntity>,
    val baseUrl: ImageEntity,
    val baseSecureUrl: ImageEntity,
    val languages: List<LanguageEntity>,
    val countries: List<CountryEntity>
)