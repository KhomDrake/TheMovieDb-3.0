package com.vlv.network.repository

import com.vlv.network.api.ConfigurationApi
import com.vlv.network.data.configuration.ConfigurationData
import com.vlv.network.database.TheMovieDbDao
import com.vlv.network.database.data.CountryEntity
import com.vlv.network.database.data.ImageEntity
import com.vlv.network.database.data.ImageType
import com.vlv.network.database.data.LanguageEntity
import com.vlv.network.datastore.DataVault

class ConfigurationRepository(
    private val api: ConfigurationApi,
    private val dao: TheMovieDbDao
) {

    suspend fun setupDataVault() = runCatching {
        val data = getConfig()

        DataVault.setValue(ImageType.BASE_URL.name, data.baseUrl.data)
        DataVault.setValue(ImageType.SECURE_BASE_URL.name, data.baseSecureUrl.data)

        DataVault.setValue(ImageType.BACKDROP.name, data.backdropSizes.first().data)
        DataVault.setValue(ImageType.LOGO.name, data.logoSizes.first().data)
        DataVault.setValue(ImageType.POSTER.name, data.posterSizes.first().data)
        DataVault.setValue(ImageType.PROFILE.name, data.profileSizes.first().data)
    }

    suspend fun loadConfig() {
        if(DataVault.getValue(ImageType.BASE_URL.name) != null) return

        val config = api.configuration()
        val languages = api.languages()
        val countries = api.countries()

        val imageItems = mutableListOf<ImageEntity>().apply {
            add(ImageEntity(config.images.baseUrl, ImageType.BASE_URL))
            add(ImageEntity(config.images.secureBaseUrl, ImageType.SECURE_BASE_URL))

            addAll(config.images.backdropSizes.map { ImageEntity(it, ImageType.BACKDROP) })
            addAll(config.images.logoSizes.map { ImageEntity(it, ImageType.LOGO) })
            addAll(config.images.profileSizes.map { ImageEntity(it, ImageType.PROFILE) })
            addAll(config.images.posterSizes.map { ImageEntity(it, ImageType.POSTER) })
        }

        dao.apply {
            insertImages(imageItems)

            insertLanguages(languages.map {
                LanguageEntity(it.iso6391, it.englishName, it.name)
            })

            insertCountries(countries.map {
                CountryEntity(it.iso31661, it.englishName, it.nativeName)
            })
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