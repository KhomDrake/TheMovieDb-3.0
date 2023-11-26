package com.vlv.configuration.data

import androidx.annotation.StringRes
import com.vlv.configuration.domain.model.SettingsData
import com.vlv.configuration.domain.model.SettingsOption
import com.vlv.configuration.presentation.R
import kotlin.random.Random

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

enum class SectionItemType {
    SWITCH,
    LIST
}

data class SettingsItemUI(
    val value: Any,
    @StringRes
    val name: Int = -1,
    val nameString: String = "",
    val id: Int = Random.nextInt(Int.MIN_VALUE, Int.MAX_VALUE)
)

data class SettingsSectionUI(
    @StringRes
    val sectionTitle: Int,
    @StringRes
    val sectionBody: Int,
    val sectionData: List<SettingsSectionItemsUI>,
    val id: Int = Random.nextInt(Int.MIN_VALUE, Int.MAX_VALUE)
)

data class SettingsSectionItemsUI(
    val option: SettingsOption,
    val type: SectionItemType,
    val items: List<SettingsItemUI>,
    val selectedItem: SettingsItemUI,
    @StringRes
    val title: Int = -1,
    @StringRes
    val description: Int = -1,
    val id: Int = Random.nextInt(Int.MIN_VALUE, Int.MAX_VALUE)
)

data class SettingsDataUI(
    val sections: List<SettingsSectionUI>
) {
    constructor(settingsData: SettingsData) : this(
        listOf(
            SettingsSectionUI(
                R.string.configuration_options_header_general,
                R.string.configuration_options_header_general,
                listOf(
                    SettingsSectionItemsUI(
                        settingsData.adultContent.section,
                        SectionItemType.SWITCH,
                        listOf(),
                        SettingsItemUI(
                            settingsData.adultContent.selectedItem.value
                        ),
                        description = R.string.configuration_options_item_adult_content_title,
                    ),
                    SettingsSectionItemsUI(
                        settingsData.adultContent.section,
                        SectionItemType.LIST,
                        settingsData.languages.items.map {
                            SettingsItemUI(
                                it.value,
                                nameString = it.name as String
                            )
                        },
                        SettingsItemUI(
                            settingsData.languages.selectedItem.value,
                            nameString = settingsData.languages.selectedItem.value as String
                        ),
                        title = R.string.configuration_options_item_language_title,
                        description = R.string.configuration_options_item_language_body,
                    )
                )
            ),
            SettingsSectionUI(
                R.string.configuration_options_header_image_definition,
                R.string.configuration_options_header_image_definition,
                listOf(
                    SettingsSectionItemsUI(
                        settingsData.backdrop.section,
                        SectionItemType.LIST,
                        settingsData.backdrop.items.run {
                            val backdropSizes = BackdropSizes.values()
                            map { item ->
                                SettingsItemUI(
                                    item.value,
                                    backdropSizes.first {
                                        it.name == (item.value as String).uppercase()
                                    }.title
                                )
                            }
                        },
                        settingsData.backdrop.selectedItem.let { item ->
                            val backdropSizes = BackdropSizes.values()
                            SettingsItemUI(
                                item.value,
                                backdropSizes.first {
                                    it.name == (item.value as String).uppercase()
                                }.title
                            )
                        },
                        R.string.configuration_options_item_backdrop_title,
                        R.string.configuration_options_item_backdrop_body
                    )
                )
            )
        )
    )
}