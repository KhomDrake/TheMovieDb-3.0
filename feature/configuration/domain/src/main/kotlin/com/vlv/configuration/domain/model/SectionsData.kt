package com.vlv.configuration.domain.model

import android.content.res.Resources
import androidx.annotation.StringRes
import com.vlv.configuration.domain.R
import com.vlv.data.local.datastore.DataVault
import com.vlv.extensions.idInt

enum class ConfigItemType {
    SWITCH,
    LIST
}

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

class SectionsData(
    val sections: List<Section>
) {
    constructor(resources: Resources, settingsData: SettingsResponse) : this(
        listOf(
            Section(
                resources.getString(R.string.configuration_options_header_general),
                listOf(
                    SectionConfig(
                        SettingOption.ADULT_CONTENT,
                        ConfigItemType.SWITCH,
                        resources.getString(R.string.configuration_options_item_adult_content_title),
                        resources.getString(R.string.configuration_options_item_adult_content_body),
                        settingsData.adultContent.selectedItem.value as Boolean
                    ),
                    SectionConfig(
                        SettingOption.DYNAMIC_COLORS,
                        ConfigItemType.SWITCH,
                        resources.getString(R.string.configuration_options_item_dynamic_colors_title),
                        resources.getString(R.string.configuration_options_item_dynamic_colors_body),
                        settingsData.dynamicColors.selectedItem.value as Boolean
                    ),
                    SectionConfig(
                        SettingOption.LANGUAGE,
                        ConfigItemType.LIST,
                        resources.getString(R.string.configuration_options_item_language_title),
                        resources.getString(R.string.configuration_options_item_language_body),
                        settingsData.run {
                            val selectedLanguageValue = DataVault.cachedDataString(
                                SettingOption.LANGUAGE.name
                            )

                            val languages = this.languages.items.map {
                                ConfigDataItemList(
                                    it.name,
                                    it.value as String
                                )
                            }

                            val selectedLanguage = languages.first {
                                it.value == selectedLanguageValue
                            }

                            ConfigDataList(
                                resources.getString(R.string.configuration_bottom_sheet_language_title),
                                resources.getString(R.string.configuration_bottom_sheet_language_body),
                                selectedLanguage,
                                languages
                            )
                        }
                    )
                )
            ),
            Section(
                resources.getString(R.string.configuration_options_header_image_definition),
                listOf(
                    SectionConfig(
                        SettingOption.LOGO,
                        ConfigItemType.LIST,
                        resources.getString(R.string.configuration_options_item_logo_title),
                        resources.getString(R.string.configuration_options_item_logo_body),
                        settingsData.run {
                            val selectedLogoValue = DataVault.cachedDataString(
                                SettingOption.LOGO.name
                            )

                            val logos = LogoSizes.values().map {
                                ConfigDataItemList(
                                    resources.getString(it.title),
                                    it.name.lowercase()
                                )
                            }

                            val selectedLogo = logos.first {
                                it.value == selectedLogoValue
                            }

                            ConfigDataList(
                                resources.getString(R.string.configuration_bottom_sheet_logo_title),
                                resources.getString(R.string.configuration_bottom_sheet_logo_body),
                                selectedLogo,
                                logos
                            )
                        }
                    ),
                    SectionConfig(
                        SettingOption.BACKDROP,
                        ConfigItemType.LIST,
                        resources.getString(R.string.configuration_options_item_backdrop_title),
                        resources.getString(R.string.configuration_options_item_backdrop_body),
                        settingsData.run {
                            val selectedBackdropValue = DataVault.cachedDataString(
                                SettingOption.BACKDROP.name
                            )

                            val logos = BackdropSizes.values().map {
                                ConfigDataItemList(
                                    resources.getString(it.title),
                                    it.name.lowercase()
                                )
                            }

                            val selectedLogo = logos.first {
                                it.value == selectedBackdropValue
                            }

                            ConfigDataList(
                                resources.getString(
                                    R.string.configuration_bottom_sheet_backdrop_title
                                ),
                                resources.getString(
                                    R.string.configuration_bottom_sheet_backdrop_body
                                ),
                                selectedLogo,
                                logos
                            )
                        }
                    ),
                    SectionConfig(
                        SettingOption.POSTER,
                        ConfigItemType.LIST,
                        resources.getString(R.string.configuration_options_item_poster_title),
                        resources.getString(R.string.configuration_options_item_poster_body),
                        settingsData.run {
                            val selectedLogoValue = DataVault.cachedDataString(
                                SettingOption.POSTER.name
                            )

                            val logos = PosterSizes.values().map {
                                ConfigDataItemList(
                                    resources.getString(it.title),
                                    it.name.lowercase()
                                )
                            }

                            val selectedLogo = logos.first {
                                it.value == selectedLogoValue
                            }

                            ConfigDataList(
                                resources.getString(
                                    R.string.configuration_bottom_sheet_poster_title
                                ),
                                resources.getString(
                                    R.string.configuration_bottom_sheet_poster_body
                                ),
                                selectedLogo,
                                logos
                            )
                        }
                    ),
                    SectionConfig(
                        SettingOption.PROFILE,
                        ConfigItemType.LIST,
                        resources.getString(R.string.configuration_options_item_profile_title),
                        resources.getString(R.string.configuration_options_item_profile_body),
                        settingsData.run {
                            val selectedLogoValue = DataVault.cachedDataString(
                                SettingOption.PROFILE.name
                            )

                            val logos = ProfileSizes.values().map {
                                ConfigDataItemList(
                                    resources.getString(it.title),
                                    it.name.lowercase()
                                )
                            }

                            val selectedLogo = logos.first {
                                it.value == selectedLogoValue
                            }

                            ConfigDataList(
                                resources.getString(
                                    R.string.configuration_bottom_sheet_profile_title
                                ),
                                resources.getString(
                                    R.string.configuration_bottom_sheet_profile_body
                                ),
                                selectedLogo,
                                logos
                            )
                        }
                    )
                )
            )
        )
    )
}

data class Section(
    val title: String,
    val configs: List<SectionConfig>,
    val id: Int = idInt()
)

data class SectionConfig(
    val configOption: SettingOption,
    val type: ConfigItemType,
    val title: String? = null,
    val description: String? = null,
    var data: Any,
    val id: Int = idInt()
)

data class ConfigDataList(
    val title: String?,
    val description: String?,
    val selectedItem: ConfigDataItemList,
    val items: List<ConfigDataItemList>
)

data class ConfigDataItemList(
    val title: String?,
    val value: String,
    val id: Int = idInt()
)