package com.vlv.configuration.domain.model

import com.vlv.configuration.data.model.ConfigurationData
import com.vlv.data.local.datastore.DataVault

enum class SettingOption {
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

class SettingsResponseSection(
    val section: SettingOption,
    val items: List<SettingsItem>,
    val selectedItem: SettingsItem
)

class SettingsResponse(
    val adultContent: SettingsResponseSection,
    val backdrop: SettingsResponseSection,
    val languages: SettingsResponseSection,
    val regions: SettingsResponseSection
) {
    constructor(configData: ConfigurationData) : this(
        adultContent = SettingsResponseSection(
            SettingOption.ADULT_CONTENT,
            listOf(),
            SettingsItem(
                DataVault.cachedDataBoolean(SettingOption.ADULT_CONTENT.name)
            )
        ),
        backdrop = configData.backdropSizes.run {
            val items = this.map { SettingsItem(it.data) }
            val previousSelected = DataVault.cachedDataString(SettingOption.BACKDROP.name)
            SettingsResponseSection(
                SettingOption.BACKDROP,
                items,
                items.first { it.value == previousSelected }
            )
        },
        languages = configData.languages.run {
            val items = this.map {
                SettingsItem(
                    it.isoName,
                    it.englishName
                )
            }
            val previousSelected = DataVault.cachedDataString(SettingOption.LANGUAGE.name)
            SettingsResponseSection(
                SettingOption.LANGUAGE,
                items,
                items.first { it.value == previousSelected }
            )
        },
        regions = configData.countries.run {
            val items = this.map {
                SettingsItem(
                    it.isoName,
                    it.nativeName.ifEmpty { it.englishName }
                )
            }

            val previousSelected = DataVault.cachedDataString(SettingOption.REGION.name)

            SettingsResponseSection(
                SettingOption.REGION,
                items,
                items.first { it.value == previousSelected }
            )
        }
    )
}
