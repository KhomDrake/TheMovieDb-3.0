package com.vlv.configuration.ui

import android.content.res.Resources
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.arch.toolkit.livedata.response.MutableResponseLiveData
import br.com.arch.toolkit.livedata.response.ResponseLiveData
import com.vlv.bondsmith.bondsmith
import com.vlv.configuration.R
import com.vlv.configuration.data.SettingsData
import com.vlv.network.datastore.DataVault
import com.vlv.network.repository.ConfigurationRepository
import com.vlv.network.repository.SettingsOption
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val resources: Resources,
    private val repository: ConfigurationRepository
) : ViewModel() {

    private val _config: MutableResponseLiveData<SettingsData> = MutableResponseLiveData()

    val config: ResponseLiveData<SettingsData>
        get() = _config

    fun config() = bondsmith<SettingsData>()
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
        .onNext {
            _config.postData(it)
        }

    fun options() = run {
        listOf(
            SettingsItem(
                resources.getString(R.string.configuration_options_header_general),
                SettingsItemType.TITLE,
                resources.getString(R.string.configuration_options_header_general),
            ),
            SettingsItem(
                SettingsOption.ADULT_CONTENT.name,
                SettingsItemType.SWITCH,
                resources.getString(R.string.configuration_options_item_adult_content_title),
                value = DataVault.cachedDataBoolean(SettingsOption.ADULT_CONTENT.name)
            ),
            SettingsItem(
                SettingsOption.LANGUAGE.name,
                SettingsItemType.NORMAL,
                resources.getString(R.string.configuration_options_item_language_title),
                resources.getString(
                    R.string.configuration_options_item_language_body,
                    DataVault.cachedDataString(SettingsOption.LANGUAGE.name)
                )
            ),
            SettingsItem(
                SettingsOption.REGION.name,
                SettingsItemType.NORMAL,
                resources.getString(R.string.configuration_options_item_region_title),
                resources.getString(
                    R.string.configuration_options_item_region_body,
                    DataVault.cachedDataString(SettingsOption.REGION.name)
                )
            ),
            SettingsItem(
                resources.getString(R.string.configuration_options_header_image_definition),
                SettingsItemType.TITLE,
                resources.getString(R.string.configuration_options_header_image_definition)
            ),
            SettingsItem(
                SettingsOption.BACKDROP.name,
                SettingsItemType.NORMAL,
                resources.getString(R.string.configuration_options_item_backdrop_title),
                resources.getString(
                    R.string.configuration_options_item_backdrop_body,
                    DataVault.cachedDataString(SettingsOption.BACKDROP.name.lowercase())
                )
            ),
            SettingsItem(
                SettingsOption.LOGO.name,
                SettingsItemType.NORMAL,
                resources.getString(R.string.configuration_options_item_logo_title),
                resources.getString(
                    R.string.configuration_options_item_logo_body,
                    DataVault.cachedDataString(SettingsOption.LOGO.name.lowercase())
                )
            ),
            SettingsItem(
                SettingsOption.POSTER.name,
                SettingsItemType.NORMAL,
                resources.getString(R.string.configuration_options_item_poster_title),
                resources.getString(
                    R.string.configuration_options_item_poster_body,
                    DataVault.cachedDataString(SettingsOption.POSTER.name.lowercase())
                )
            ),
            SettingsItem(
                SettingsOption.PROFILE.name,
                SettingsItemType.NORMAL,
                resources.getString(R.string.configuration_options_item_profile_title),
                resources.getString(
                    R.string.configuration_options_item_profile_body,
                    DataVault.cachedDataString(SettingsOption.PROFILE.name.lowercase())
                )
            )
        )
    }

    fun setConfigValue(title: String?, key: String, value: String) {
        viewModelScope.launch {
            title?.let { DataVault.setValue(key.lowercase(), it) }
            DataVault.setValue(key, value)
        }
    }

    fun getConfigValue(key: String) = DataVault.cachedDataString(key)

    fun setConfigValue(key: String, value: Boolean) {
        viewModelScope.launch {
            DataVault.setValue(key, value)
        }
    }

}