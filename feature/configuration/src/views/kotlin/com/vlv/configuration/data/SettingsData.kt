package com.vlv.configuration.data

import com.vlv.imperiya.ui.bottomsheet.Item
import com.vlv.network.data.configuration.ConfigurationData
import com.vlv.network.datastore.DataVault
import com.vlv.network.repository.SettingsOption

class SettingsData(
    val backdropSizes: List<Item>,
    val posterSizes: List<Item>,
    val profileSizes: List<Item>,
    val logoSizes: List<Item>,
    val languages: List<Item>,
    val countries: List<Item>,
    val baseUrl: String,
    val baseSecureUrl: String
) {
    constructor(configData: ConfigurationData, dataVault: DataVault) : this(
        configData.backdropSizes.run {
            val previousSelected = dataVault.cachedDataString(SettingsOption.BACKDROP.name)
            map {
                Item(
                    it.data,
                    it.data,
                    checked = previousSelected == it.data
                )
            }
        },
        configData.posterSizes.run {
            val previousSelected = dataVault.cachedDataString(SettingsOption.POSTER.name)
            map {
                Item(
                    it.data,
                    it.data,
                    checked = previousSelected == it.data
                )
            }
        },
        configData.profileSizes.run {
            val previousSelected = dataVault.cachedDataString(SettingsOption.PROFILE.name)
            map {
                Item(
                    it.data,
                    it.data,
                    checked = previousSelected == it.data
                )
            }
        },
        configData.logoSizes.run {
            val previousSelected = dataVault.cachedDataString(SettingsOption.LOGO.name)
            map {
                Item(
                    it.data,
                    it.data,
                    checked = previousSelected == it.data
                )
            }
        },
        configData.languages.run {
            val previousSelected = dataVault.cachedDataString(SettingsOption.LANGUAGE.name)
            map {
                Item(
                    it.englishName,
                    it.name,
                    checked = previousSelected == it.name
                )
            }
        },
        configData.countries.run {
            val previousSelected = dataVault.cachedDataString(SettingsOption.REGION.name)
            map {
                Item(
                    it.englishName,
                    it.isoName,
                    checked = previousSelected == it.nativeName
                )
            }
        },
        configData.baseUrl.data,
        configData.baseSecureUrl.data
    )
}