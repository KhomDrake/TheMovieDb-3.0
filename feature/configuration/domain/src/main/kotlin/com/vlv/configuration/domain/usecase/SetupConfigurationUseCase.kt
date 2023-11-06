package com.vlv.configuration.domain.usecase

import android.content.res.Resources
import com.vlv.configuration.data.ConfigurationRepository
import com.vlv.configuration.data.model.ConfigurationData
import com.vlv.configuration.domain.R
import com.vlv.configuration.domain.model.SettingsOption
import com.vlv.data.local.datastore.DataVault
class SetupConfigurationUseCase(
    private val repository: ConfigurationRepository
) {
    suspend fun loadConfig(resources: Resources) {
        val keysNotLoaded = SettingsOption.values().filter { DataVault.containsKey(it.name).not() }

        val data = if(keysNotLoaded.isEmpty()) repository.getConfig()
            else repository.defaultConfigData()

        if(keysNotLoaded.isNotEmpty()) repository.updateDatabase(data)

        DataVault.loadCache()
        loadConfigDataToCache(resources, data)
    }

    private suspend fun loadConfigDataToCache(resources: Resources, data: ConfigurationData) {
        DataVault.setValue(SettingsOption.BASE_URL.name, data.baseUrl.data)
        DataVault.setValue(SettingsOption.SECURE_BASE_URL.name, data.baseSecureUrl.data)

        DataVault.setValue(SettingsOption.BACKDROP.name, data.backdropSizes.first().data)
        DataVault.setValue(SettingsOption.LOGO.name, data.logoSizes.first().data)
        DataVault.setValue(SettingsOption.POSTER.name, data.posterSizes.first().data)
        DataVault.setValue(SettingsOption.PROFILE.name, data.profileSizes.first().data)

        DataVault.setValue(SettingsOption.ADULT_CONTENT.name, false)

        data.languages.find {
            it.name == resources.getString(R.string.configuration_language_default)
        }?.name?.let {
            DataVault.setValue(
                SettingsOption.LANGUAGE.name, it
            )
        }

        data.countries.find {
            it.isoName == resources.getString(R.string.configuration_country_default)
        }?.isoName?.let {
            DataVault.setValue(
                SettingsOption.REGION.name, it
            )
        }
    }
}