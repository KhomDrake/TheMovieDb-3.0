package com.vlv.configuration.domain.usecase

import android.content.res.Resources
import com.vlv.bondsmith.bondsmith
import com.vlv.configuration.data.ConfigurationRepository
import com.vlv.configuration.domain.model.SettingsData
import com.vlv.configuration.domain.model.SettingsOption
import com.vlv.data.local.datastore.DataVault

class SettingsUseCase(
    private val repository: ConfigurationRepository
) {

    fun config(resources: Resources) = bondsmith<SettingsData>()
        .request {
            val config = SettingsData(resources, repository.getConfig())

            config.backdropSizes.firstOrNull { it.checked }?.let {
                DataVault.setValue(SettingsOption.BACKDROP.name.lowercase(), it.name)
            }
            config.logoSizes.firstOrNull { it.checked }?.let {
                DataVault.setValue(SettingsOption.LOGO.name.lowercase(), it.name)
            }
            config.profileSizes.firstOrNull { it.checked }?.let {
                DataVault.setValue(SettingsOption.PROFILE.name.lowercase(), it.name)
            }
            config.posterSizes.firstOrNull { it.checked }?.let {
                DataVault.setValue(SettingsOption.POSTER.name.lowercase(), it.name)
            }
            config
        }
        .execute()
        .responseLiveData

    suspend fun setConfigValue(title: String?, key: String, value: String) {
        title?.let { DataVault.setValue(key.lowercase(), it) }
        DataVault.setValue(key, value)
    }

    suspend fun setConfigValue(key: String, value: Boolean) {
        DataVault.setValue(key, value)
    }

    fun getConfigValue(key: String) = DataVault.cachedDataString(key)

    fun getConfigValueBoolean(key: String) = DataVault.cachedDataBoolean(key)

}