package com.vlv.configuration.presentation.ui

import com.vlv.configuration.domain.model.SettingsData
import com.vlv.imperiya.core.ui.bottomsheet.Item

data class SettingsDataUI(
    val backdropSizes: List<Item>,
    val posterSizes: List<Item>,
    val profileSizes: List<Item>,
    val logoSizes: List<Item>,
    val languages: List<Item>,
    val countries: List<Item>,
    val baseUrl: String,
    val baseSecureUrl: String
) {

    constructor(settingsData: SettingsData) : this(
        backdropSizes = settingsData.backdropSizes.map { Item(
            it.name,
            it.value,
            it.checked
        ) },
        posterSizes = settingsData.posterSizes.map { Item(
            it.name,
            it.value,
            it.checked
        ) },
        profileSizes = settingsData.profileSizes.map { Item(
            it.name,
            it.value,
            it.checked
        ) },
        logoSizes = settingsData.logoSizes.map { Item(
            it.name,
            it.value,
            it.checked
        ) },
        languages = settingsData.languages.map { Item(
            it.name,
            it.value,
            it.checked
        ) },
        countries = settingsData.countries.map { Item(
            it.name,
            it.value,
            it.checked
        ) },
        baseUrl = settingsData.baseUrl,
        baseSecureUrl = settingsData.baseSecureUrl,
    )

}