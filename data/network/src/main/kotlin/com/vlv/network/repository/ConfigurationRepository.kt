package com.vlv.network.repository

import android.content.res.Resources
import android.util.Log
import com.vlv.network.R
import com.vlv.network.api.ConfigurationApi
import com.vlv.network.data.configuration.ConfigurationData
import com.vlv.network.database.TheMovieDbDao
import com.vlv.network.database.data.CountryEntity
import com.vlv.network.database.data.ImageEntity
import com.vlv.network.database.data.ImageType
import com.vlv.network.database.data.LanguageEntity
import com.vlv.network.datastore.DataVault

enum class SettingsOption {
    ADULT_CONTENT,
    LANGUAGE,
    REGION,
    BACKDROP,
    LOGO,
    POSTER,
    PROFILE,
    BASE_URL,
    SECURE_BASE_URL
}

class ConfigurationRepository(
    private val resources: Resources,
    private val api: ConfigurationApi,
    private val dao: TheMovieDbDao
) {

    private suspend fun configDataVault() = runCatching {
        val data = getConfig()

        DataVault.setValue(SettingsOption.BASE_URL.name, data.baseUrl.data)
        DataVault.setValue(SettingsOption.SECURE_BASE_URL.name, data.baseSecureUrl.data)

        DataVault.setValue(SettingsOption.BACKDROP.name, data.backdropSizes.first().data)
        DataVault.setValue(SettingsOption.LOGO.name, data.logoSizes.first().data)
        DataVault.setValue(SettingsOption.POSTER.name, data.posterSizes.first().data)
        DataVault.setValue(SettingsOption.PROFILE.name, data.profileSizes.first().data)

        DataVault.setValue(SettingsOption.ADULT_CONTENT.name, false)

        data.languages.find {
            it.name == resources.getString(R.string.network_language_default)
        }?.name?.let {
            DataVault.setValue(
                SettingsOption.LANGUAGE.name, it
            )
        }

        data.countries.find {
            it.isoName == resources.getString(R.string.network_country_default)
        }?.isoName?.let {
            DataVault.setValue(
                SettingsOption.REGION.name, it
            )
        }
    }

    suspend fun loadConfig() {
        val keysNotLoaded = SettingsOption.values().filter { DataVault.containsKey(it.name).not() }

        if(keysNotLoaded.isEmpty()) return

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
            removeLanguages()
            removeCountries()
            removeImages()

            insertImages(imageItems)

            insertLanguages(languages.map {
                LanguageEntity(it.iso6391, it.englishName, it.name)
            })

            insertCountries(countries.map {
                CountryEntity(it.iso31661, it.englishName, it.nativeName)
            })
        }

        configDataVault()
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