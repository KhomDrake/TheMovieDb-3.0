package com.vlv.configuration.domain.model

import android.content.res.Resources
import com.vlv.configuration.data.model.ConfigurationData
import com.vlv.configuration.domain.R
import com.vlv.data.local.datastore.DataVault

enum class SettingOption {
    ADULT_CONTENT,
    DYNAMIC_COLORS,
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
    val dynamicColors: SettingsResponseSection,
    val backdrop: SettingsResponseSection,
    val languages: SettingsResponseSection
) {
    constructor(
        resources: Resources,
        configData: ConfigurationData
    ) : this(
        adultContent = SettingsResponseSection(
            SettingOption.ADULT_CONTENT,
            listOf(),
            SettingsItem(
                DataVault.cachedDataBoolean(SettingOption.ADULT_CONTENT.name)
            )
        ),
        dynamicColors = SettingsResponseSection(
            SettingOption.DYNAMIC_COLORS,
            listOf(),
            SettingsItem(
                DataVault.cachedDataBoolean(SettingOption.DYNAMIC_COLORS.name)
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
            val permitted = resources.getStringArray(R.array.configuration_permitted_languages)
                .toSet()
            val items = this.filter { permitted.contains(it.isoName) } .map {
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
        }
    )
}
