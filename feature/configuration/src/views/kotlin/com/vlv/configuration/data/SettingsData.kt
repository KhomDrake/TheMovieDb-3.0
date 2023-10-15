package com.vlv.configuration.data

import android.content.res.Resources
import android.support.annotation.StringRes
import com.vlv.configuration.R
import com.vlv.imperiya.ui.bottomsheet.Item
import com.vlv.network.data.configuration.ConfigurationData
import com.vlv.network.datastore.DataVault
import com.vlv.network.repository.SettingsOption

enum class BackdropSizes(
    @StringRes
    val title: Int
) {
    W300(R.string.configuration_backdrop_low),
    W780(R.string.configuration_backdrop_medium),
    W1280(R.string.configuration_backdrop_high),
    ORIGINAL(R.string.configuration_backdrop_original),
}

enum class LogoSizes(
    @StringRes
    val title: Int
) {
    W45(R.string.configuration_logo_lowest),
    W92(R.string.configuration_logo_low),
    W154(R.string.configuration_logo_medium_low),
    W185(R.string.configuration_logo_medium),
    W300(R.string.configuration_logo_high),
    W500(R.string.configuration_logo_highest),
    ORIGINAL(R.string.configuration_logo_original)
}

enum class PosterSizes(
    @StringRes
    val title: Int
) {
    W92(R.string.configuration_poster_lowest),
    W154(R.string.configuration_poster_low),
    W185(R.string.configuration_poster_medium_low),
    W342(R.string.configuration_poster_medium),
    W500(R.string.configuration_poster_high),
    W780(R.string.configuration_poster_highest),
    ORIGINAL(R.string.configuration_poster_original)
}

enum class ProfileSizes(
    @StringRes
    val title: Int
) {
    W45(R.string.configuration_profile_low),
    W185(R.string.configuration_profile_medium),
    H632(R.string.configuration_profile_high),
    ORIGINAL(R.string.configuration_profile_original)
}

data class SettingsData(
    private val _backdropSizes: List<Item>,
    private val _posterSizes: List<Item>,
    private val _profileSizes: List<Item>,
    private val _logoSizes: List<Item>,
    private val _languages: List<Item>,
    private val _countries: List<Item>,
    val baseUrl: String,
    val baseSecureUrl: String
) {

    val backdropSizes: List<Item>
        get() = run {
            val previousSelected = DataVault.cachedDataString(SettingsOption.BACKDROP.name)
            _backdropSizes.map { item ->
                item.copy(checked = previousSelected == item.value)
            }
        }

    val posterSizes: List<Item>
        get() = run {
            val previousSelected = DataVault.cachedDataString(SettingsOption.POSTER.name)
            _posterSizes.map { item ->
                item.copy(checked = previousSelected == item.value)
            }
        }

    val profileSizes: List<Item>
        get() = run {
            val previousSelected = DataVault.cachedDataString(SettingsOption.PROFILE.name)
            _profileSizes.map { item ->
                item.copy(checked = previousSelected == item.value)
            }
        }

    val logoSizes: List<Item>
        get() = run {
            val previousSelected = DataVault.cachedDataString(SettingsOption.LOGO.name)
            _logoSizes.map { item ->
                item.copy(checked = previousSelected == item.value)
            }
        }

    val languages: List<Item>
        get() = run {
            val previousSelected = DataVault.cachedDataString(SettingsOption.LANGUAGE.name)
            _languages.map { item ->
                item.copy(checked = previousSelected == item.value)
            }
        }

    val countries: List<Item>
        get() = run {
            val previousSelected = DataVault.cachedDataString(SettingsOption.REGION.name)
            _countries.map { item ->
                item.copy(checked = previousSelected == item.value)
            }
        }


    constructor(resources: Resources, configData: ConfigurationData) : this(
        configData.backdropSizes.run {
            val titles = BackdropSizes.values().map { Pair(
                resources.getString(it.title),
                it.name.lowercase()
            ) }
            titles.map {
                Item(
                    it.first,
                    it.second
                )
            }
        },
        configData.posterSizes.run {
            val titles = PosterSizes.values().map { Pair(
                resources.getString(it.title),
                it.name.lowercase()
            ) }
            titles.map {
                Item(
                    it.first,
                    it.second
                )
            }
        },
        configData.profileSizes.run {
            val titles = ProfileSizes.values().map { Pair(
                resources.getString(it.title),
                it.name.lowercase()
            ) }
            titles.map {
                Item(
                    it.first,
                    it.second
                )
            }
        },
        configData.logoSizes.run {
            val titles = LogoSizes.values().map { Pair(
                resources.getString(it.title),
                it.name.lowercase()
            ) }
            titles.map {
                Item(
                    it.first,
                    it.second
                )
            }
        },
        configData.languages.run {
            map {
                Item(
                    it.englishName,
                    it.name
                )
            }
        },
        configData.countries.run {
            map {
                Item(
                    it.englishName,
                    it.isoName
                )
            }
        },
        configData.baseUrl.data,
        configData.baseSecureUrl.data
    )
}