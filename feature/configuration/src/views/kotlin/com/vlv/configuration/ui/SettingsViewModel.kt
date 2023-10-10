package com.vlv.configuration.ui

import androidx.lifecycle.ViewModel
import com.vlv.bondsmith.bondsmith
import com.vlv.network.data.configuration.ConfigurationData
import com.vlv.network.repository.ConfigurationRepository

class SettingsViewModel(
    private val repository: ConfigurationRepository
) : ViewModel() {

    fun config() = bondsmith<ConfigurationData>()
        .request {
            repository.getConfig()
        }
        .execute()
        .responseLiveData

}