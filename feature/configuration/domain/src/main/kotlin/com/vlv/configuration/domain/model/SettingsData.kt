package com.vlv.configuration.domain.model

import com.vlv.configuration.data.model.ConfigurationData
import com.vlv.data.local.datastore.DataVault

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

class SettingsItem(
    val value: Any,
    val name: String? = null
)

class SettingsSection(
    val section: SettingsOption,
    val items: List<SettingsItem>,
    val selectedItem: SettingsItem
)

class SettingsData(
    val adultContent: SettingsSection,
    val backdrop: SettingsSection,
    val languages: SettingsSection
) {
    constructor(configData: ConfigurationData) : this(
        adultContent = SettingsSection(
            SettingsOption.ADULT_CONTENT,
            listOf(),
            SettingsItem(
                DataVault.cachedDataBoolean(SettingsOption.ADULT_CONTENT.name)
            )
        ),
        backdrop = configData.backdropSizes.run {
            val items = this.map { SettingsItem(it.data) }
            val previousSelected = DataVault.cachedDataString(SettingsOption.BACKDROP.name)
            SettingsSection(
                SettingsOption.BACKDROP,
                items,
                items.first { it.value == previousSelected }
            )
        },
        languages = configData.languages.run {
            val items = this.map {
                SettingsItem(
                    it.name,
                    it.englishName
                )
            }
            val previousSelected = DataVault.cachedDataString(SettingsOption.LANGUAGE.name)
            SettingsSection(
                SettingsOption.LANGUAGE,
                items,
                items.first { it.value == previousSelected }
            )
        }
    )
}

//data class SettingsData(
//    private val _backdropSizes: List<SettingsItem>,
//    private val _posterSizes: List<SettingsItem>,
//    private val _profileSizes: List<SettingsItem>,
//    private val _logoSizes: List<SettingsItem>,
//    private val _languages: List<SettingsItem>,
//    private val _countries: List<SettingsItem>,
//    val baseUrl: String,
//    val baseSecureUrl: String
//) {
//
//    val backdropSizes: List<SettingsItem>
//        get() = run {
//            val previousSelected = DataVault.cachedDataString(SettingsOption.BACKDROP.name)
//            _backdropSizes.map { item ->
//                item.copy(checked = previousSelected == item.value)
//            }
//        }
//
//    val posterSizes: List<SettingsItem>
//        get() = run {
//            val previousSelected = DataVault.cachedDataString(SettingsOption.POSTER.name)
//            _posterSizes.map { item ->
//                item.copy(checked = previousSelected == item.value)
//            }
//        }
//
//    val profileSizes: List<SettingsItem>
//        get() = run {
//            val previousSelected = DataVault.cachedDataString(SettingsOption.PROFILE.name)
//            _profileSizes.map { item ->
//                item.copy(checked = previousSelected == item.value)
//            }
//        }
//
//    val logoSizes: List<SettingsItem>
//        get() = run {
//            val previousSelected = DataVault.cachedDataString(SettingsOption.LOGO.name)
//            _logoSizes.map { item ->
//                item.copy(checked = previousSelected == item.value)
//            }
//        }
//
//    val languages: List<SettingsItem>
//        get() = run {
//            val previousSelected = DataVault.cachedDataString(SettingsOption.LANGUAGE.name)
//            _languages.map { item ->
//                item.copy(checked = previousSelected == item.value)
//            }
//        }
//
//    val countries: List<SettingsItem>
//        get() = run {
//            val previousSelected = DataVault.cachedDataString(SettingsOption.REGION.name)
//            _countries.map { item ->
//                item.copy(checked = previousSelected == item.value)
//            }
//        }
//    constructor(resources: Resources, configData: ConfigurationData) : this(
//        configData.backdropSizes.run {
//            val titles = BackdropSizes.values().map { Pair(
//                resources.getString(it.title),
//                it.name.lowercase()
//            ) }
//            titles.map {
//                SettingsItem(
//                    it.first,
//                    it.second
//                )
//            }
//        },
//        configData.posterSizes.run {
//            val titles = PosterSizes.values().map { Pair(
//                resources.getString(it.title),
//                it.name.lowercase()
//            ) }
//            titles.map {
//                SettingsItem(
//                    it.first,
//                    it.second
//                )
//            }
//        },
//        configData.profileSizes.run {
//            val titles = ProfileSizes.values().map { Pair(
//                resources.getString(it.title),
//                it.name.lowercase()
//            ) }
//            titles.map {
//                SettingsItem(
//                    it.first,
//                    it.second
//                )
//            }
//        },
//        configData.logoSizes.run {
//            val titles = LogoSizes.values().map { Pair(
//                resources.getString(it.title),
//                it.name.lowercase()
//            ) }
//            titles.map {
//                SettingsItem(
//                    it.first,
//                    it.second
//                )
//            }
//        },
//        configData.languages.run {
//            map {
//                SettingsItem(
//                    it.englishName,
//                    it.name
//                )
//            }
//        },
//        configData.countries.run {
//            map {
//                SettingsItem(
//                    it.englishName,
//                    it.isoName
//                )
//            }
//        },
//        configData.baseUrl.data,
//        configData.baseSecureUrl.data
//    )
//}