package com.vlv.configuration.domain.usecase

import android.content.res.Resources
import com.vlv.configuration.data.ConfigurationRepository
import com.vlv.configuration.domain.model.ConfigDataList
import com.vlv.configuration.domain.model.ConfigItemType
import com.vlv.configuration.domain.model.SectionsData
import com.vlv.configuration.domain.model.SettingsData
import com.vlv.configuration.domain.model.SettingsOption
import com.vlv.data.local.datastore.DataVault

class SettingsUseCase(
    private val repository: ConfigurationRepository
) {

    suspend fun newConfig() : SettingsData {
        val config = repository.getConfig()

        return SettingsData(config)
    }

    suspend fun configData(resources: Resources) : SectionsData {
        val settingsData = newConfig()
        return SectionsData(resources, settingsData)
    }

    suspend fun setConfigValue(option: SettingsOption, value: Any, type: ConfigItemType) {
        when(type) {
            ConfigItemType.LIST -> {
                val data = value as ConfigDataList
                DataVault.setValue(
                    option.name,
                    data.selectedItem.value
                )
            }
            ConfigItemType.SWITCH -> {
                DataVault.setValue(
                    option.name,
                    value as Boolean
                )
            }
        }
    }

    suspend fun setConfigValue(key: String, value: Boolean) {
        DataVault.setValue(key, value)
    }

    fun getConfigValue(key: String) = DataVault.cachedDataString(key)

    fun getConfigValueBoolean(key: String) = DataVault.cachedDataBoolean(key)

}