package com.vlv.configuration.data

import android.os.Build
import com.vlv.configuration.domain.model.ConfigDataList
import com.vlv.configuration.domain.model.ConfigItemType
import com.vlv.configuration.domain.model.SectionsData
import com.vlv.configuration.domain.model.SettingOption
import com.vlv.configuration.presentation.BuildConfig

enum class SectionUIType {
    HEADER,
    SWITCH,
    LIST
}

fun SectionUIType.toConfigItemType() = run {
    when(this) {
        SectionUIType.SWITCH -> ConfigItemType.SWITCH
        else -> ConfigItemType.LIST
    }
}

data class SectionUIItem(
    val type: SectionUIType,
    val id: Int,
    var data: Any? = null,
    val title: String? = null,
    val description: String? = null,
    val settingsOption: SettingOption? = null,
) {

    val descriptionWithSelectedValue: String
        get() = "$description ${(data as? ConfigDataList)?.selectedItem?.title.toString()}"

}

fun SectionsData.toSectionUIItems(): List<SectionUIItem> {
    val items = mutableListOf<SectionUIItem>()

    val types = SectionUIType.values()

    val shouldShowDynamicColors = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S &&
        BuildConfig.IS_COMPOSE

    this.sections.forEach { section ->
        items.add(
            SectionUIItem(
                SectionUIType.HEADER,
                section.id,
                null,
                section.title
            )
        )
        section.configs.forEach { configs ->
            val sectionUIItem = SectionUIItem(
                types.first { it.name == configs.type.name },
                configs.id,
                configs.data,
                configs.title,
                configs.description,
                configs.configOption
            )

            if(
                configs.configOption != SettingOption.DYNAMIC_COLORS || shouldShowDynamicColors
            ) {
                items.add(sectionUIItem)
            }
        }
    }

    return items
}