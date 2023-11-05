package com.vlv.configuration.data

import com.vlv.configuration.data.api.ConfigurationApi
import com.vlv.configuration.data.model.ConfigurationData
import com.vlv.data.database.TheMovieDbDao
import com.vlv.data.network.database.data.CountryEntity
import com.vlv.data.network.database.data.ImageEntity
import com.vlv.data.network.database.data.ImageType
import com.vlv.data.network.database.data.LanguageEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async

private val newScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

class ConfigurationRepository(
    private val api: ConfigurationApi,
    private val dao: TheMovieDbDao
) {

    suspend fun defaultConfigData() = run {
        val config = newScope.async {
            api.configuration()
        }.await()
        val languages = newScope.async {
            api.languages()
        }.await()
        val countries = newScope.async {
            api.countries()
        }.await()

        ConfigurationData(
            config.images.backdropSizes.map { ImageEntity(it, ImageType.BACKDROP) },
            config.images.logoSizes.map { ImageEntity(it, ImageType.LOGO) },
            config.images.profileSizes.map { ImageEntity(it, ImageType.PROFILE) },
            config.images.posterSizes.map { ImageEntity(it, ImageType.POSTER) },
            ImageEntity(config.images.baseUrl, ImageType.BASE_URL),
            ImageEntity(config.images.secureBaseUrl, ImageType.SECURE_BASE_URL),
            languages.map {
                LanguageEntity(it.iso6391, it.englishName, it.name)
            },
            countries.map {
                CountryEntity(it.iso31661, it.englishName, it.nativeName)
            }
        )
    }

    suspend fun updateDatabase(configurationData: ConfigurationData) {
        dao.apply {
            removeLanguages()
            removeCountries()
            removeImages()

            newScope.async {
                insertLanguages(configurationData.languages)
            }.await()

            newScope.async {
                insertCountries(configurationData.countries)
            }.await()

            newScope.async {
                insertImages(
                    configurationData.backdropSizes +
                            configurationData.logoSizes +
                            configurationData.posterSizes +
                            configurationData.profileSizes +
                            configurationData.baseUrl +
                            configurationData.baseSecureUrl
                )
            }.await()
        }
    }

    suspend fun getConfig() : ConfigurationData {
        val images = dao.getImages().groupBy { it.type }

        return ConfigurationData(
            images[ImageType.BACKDROP] ?: listOf(),
            images[ImageType.POSTER] ?: listOf(),
            images[ImageType.PROFILE] ?: listOf(),
            images[ImageType.LOGO] ?: listOf(),
            (images[ImageType.BASE_URL] ?: listOf()).first(),
            (images[ImageType.SECURE_BASE_URL] ?: listOf()).first(),
            dao.getLanguages(),
            dao.getCountries()
        )
    }

}