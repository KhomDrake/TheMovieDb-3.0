package com.vlv.configuration.ui

import android.content.Context
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
    private val repository: ConfigurationRepository
) : ViewModel() {

    private val _config: MutableResponseLiveData<SettingsData> = MutableResponseLiveData()

    val config: ResponseLiveData<SettingsData>
        get() = _config

    fun config() = bondsmith<SettingsData>()
        .request {
            SettingsData(repository.getConfig(), DataVault)
        }
        .execute()
        .responseLiveData
        .onNext {
            _config.postData(it)
        }

    fun options(context: Context) = run {
        listOf(
            SettingsItem(
                context.getString(R.string.configuration_options_header_general),
                SettingsItemType.TITLE,
                context.getString(R.string.configuration_options_header_general),
            ),
            SettingsItem(
                SettingsOption.ADULT_CONTENT.name,
                SettingsItemType.SWITCH,
                context.getString(R.string.configuration_options_item_adult_content_title),
                value = DataVault.cachedDataBoolean(SettingsOption.ADULT_CONTENT.name)
            ),
            SettingsItem(
                SettingsOption.LANGUAGE.name,
                SettingsItemType.NORMAL,
                context.getString(R.string.configuration_options_item_language_title),
                context.getString(
                    R.string.configuration_options_item_language_body,
                    DataVault.cachedDataString(SettingsOption.LANGUAGE.name)
                )
            ),
            SettingsItem(
                SettingsOption.REGION.name,
                SettingsItemType.NORMAL,
                context.getString(R.string.configuration_options_item_region_title),
                context.getString(
                    R.string.configuration_options_item_region_body,
                    DataVault.cachedDataString(SettingsOption.REGION.name)
                )
            ),
            SettingsItem(
                context.getString(R.string.configuration_options_header_image_definition),
                SettingsItemType.TITLE,
                context.getString(R.string.configuration_options_header_image_definition)
            ),
            SettingsItem(
                SettingsOption.BACKDROP.name,
                SettingsItemType.NORMAL,
                context.getString(R.string.configuration_options_item_backdrop_title),
                context.getString(
                    R.string.configuration_options_item_backdrop_body,
                    DataVault.cachedDataString(SettingsOption.BACKDROP.name)
                )
            ),
            SettingsItem(
                SettingsOption.LOGO.name,
                SettingsItemType.NORMAL,
                context.getString(R.string.configuration_options_item_logo_title),
                context.getString(
                    R.string.configuration_options_item_logo_body,
                    DataVault.cachedDataString(SettingsOption.LOGO.name)
                )
            ),
            SettingsItem(
                SettingsOption.POSTER.name,
                SettingsItemType.NORMAL,
                context.getString(R.string.configuration_options_item_poster_title),
                context.getString(
                    R.string.configuration_options_item_poster_body,
                    DataVault.cachedDataString(SettingsOption.POSTER.name)
                )
            ),
            SettingsItem(
                SettingsOption.PROFILE.name,
                SettingsItemType.NORMAL,
                context.getString(R.string.configuration_options_item_profile_title),
                context.getString(
                    R.string.configuration_options_item_profile_body,
                    DataVault.cachedDataString(SettingsOption.PROFILE.name)
                )
            )
        )
    }

    fun setConfigValue(key: String, value: String) {
        viewModelScope.launch {
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