package com.vlv.network.data.configuration

import com.vlv.network.database.data.CountryEntity
import com.vlv.network.database.data.ImageEntity
import com.vlv.network.database.data.LanguageEntity

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