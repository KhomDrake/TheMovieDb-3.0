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
    val languages: SettingsSection,
    val regions: SettingsSection
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
                    it.isoName,
                    it.englishName
                )
            }
            val previousSelected = DataVault.cachedDataString(SettingsOption.LANGUAGE.name)
            SettingsSection(
                SettingsOption.LANGUAGE,
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

            val previousSelected = DataVault.cachedDataString(SettingsOption.REGION.name)

            SettingsSection(
                SettingsOption.REGION,
                items,
                items.first { it.value == previousSelected }
            )
        }
    )
}
