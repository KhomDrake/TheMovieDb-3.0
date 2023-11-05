package com.vlv.configuration.apresentation.ui

import android.content.res.Resources
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.arch.toolkit.livedata.response.MutableResponseLiveData
import br.com.arch.toolkit.livedata.response.ResponseLiveData
import com.vlv.configuration.apresentation.R
import com.vlv.configuration.domain.usecase.SettingsUseCase
import com.vlv.configuration.domain.model.SettingsData
import com.vlv.configuration.domain.model.SettingsOption
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val resources: Resources,
    private val useCase: SettingsUseCase
) : ViewModel() {

    private val _config: MutableResponseLiveData<SettingsDataUI> = MutableResponseLiveData()

    val config: ResponseLiveData<SettingsDataUI>
        get() = _config

    fun config() = useCase.config(resources)
        .map {
            SettingsDataUI(it)
        }
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
                value = useCase.getConfigValueBoolean(SettingsOption.ADULT_CONTENT.name)
            ),
            SettingsItem(
                SettingsOption.LANGUAGE.name,
                SettingsItemType.NORMAL,
                resources.getString(R.string.configuration_options_item_language_title),
                resources.getString(
                    R.string.configuration_options_item_language_body,
                    useCase.getConfigValue(SettingsOption.LANGUAGE.name)
                )
            ),
            SettingsItem(
                SettingsOption.REGION.name,
                SettingsItemType.NORMAL,
                resources.getString(R.string.configuration_options_item_region_title),
                resources.getString(
                    R.string.configuration_options_item_region_body,
                    useCase.getConfigValue(SettingsOption.REGION.name)
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
                    useCase.getConfigValue(SettingsOption.BACKDROP.name.lowercase())
                )
            ),
            SettingsItem(
                SettingsOption.LOGO.name,
                SettingsItemType.NORMAL,
                resources.getString(R.string.configuration_options_item_logo_title),
                resources.getString(
                    R.string.configuration_options_item_logo_body,
                    useCase.getConfigValue(SettingsOption.LOGO.name.lowercase())
                )
            ),
            SettingsItem(
                SettingsOption.POSTER.name,
                SettingsItemType.NORMAL,
                resources.getString(R.string.configuration_options_item_poster_title),
                resources.getString(
                    R.string.configuration_options_item_poster_body,
                    useCase.getConfigValue(SettingsOption.POSTER.name.lowercase())
                )
            ),
            SettingsItem(
                SettingsOption.PROFILE.name,
                SettingsItemType.NORMAL,
                resources.getString(R.string.configuration_options_item_profile_title),
                resources.getString(
                    R.string.configuration_options_item_profile_body,
                    getConfigValue(SettingsOption.PROFILE.name.lowercase())
                )
            )
        )
    }

    fun setConfigValue(title: String?, key: String, value: String) {
        viewModelScope.launch {
            useCase.setConfigValue(title, key, value)
        }
    }

    private fun getConfigValue(key: String) = useCase.getConfigValue(key)

    fun setConfigValue(key: String, value: Boolean) {
        viewModelScope.launch {
            useCase.setConfigValue(key, value)
        }
    }

}