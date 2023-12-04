package com.vlv.configuration.data

import com.vlv.configuration.domain.model.ConfigItemType
import com.vlv.configuration.domain.model.SectionsData
import com.vlv.configuration.domain.model.SettingsOption

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
    val settingsOption: SettingsOption? = null,
)

suspend fun SectionsData.toSectionUIItems(): List<SectionUIItem> {
    val items = mutableListOf<SectionUIItem>()

    val types = SectionUIType.values()

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
            items.add(
                SectionUIItem(
                    types.first { it.name == configs.type.name },
                    configs.id,
                    configs.data,
                    configs.title,
                    configs.description,
                    configs.configOption
                )
            )
        }
    }

    return items
}