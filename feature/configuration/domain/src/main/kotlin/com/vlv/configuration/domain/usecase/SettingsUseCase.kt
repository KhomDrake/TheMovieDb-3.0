package com.vlv.configuration.domain.usecase

import android.content.res.Resources
import com.vlv.bondsmith.bondsmith
import com.vlv.configuration.data.ConfigurationRepository
import com.vlv.configuration.domain.model.ConfigDataList
import com.vlv.configuration.domain.model.ConfigItemType
import com.vlv.configuration.domain.model.SectionsData
import com.vlv.configuration.domain.model.SettingOption
import com.vlv.configuration.domain.model.SettingsResponse
import com.vlv.data.local.datastore.DataVault

class SettingsUseCase(
    private val resources: Resources,
    private val repository: ConfigurationRepository
) {

    private suspend fun newConfig() : SettingsResponse {
        val config = repository.getConfig()

        return SettingsResponse(resources, config)
    }
    suspend fun configData() = bondsmith<SectionsData>("CONFIG-DATA")
        .config {
            request {
                withCache(with = false)
                val settingsData = newConfig()
                SectionsData(resources, settingsData)
            }
        }
        .execute()

    suspend fun setConfigValue(option: SettingOption, value: Any, type: ConfigItemType) {
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