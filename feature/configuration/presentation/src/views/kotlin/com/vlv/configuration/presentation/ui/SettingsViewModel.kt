package com.vlv.configuration.presentation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.arch.toolkit.livedata.response.MutableResponseLiveData
import br.com.arch.toolkit.livedata.response.ResponseLiveData
import com.vlv.configuration.data.SectionUIItem
import com.vlv.configuration.data.toConfigItemType
import com.vlv.configuration.data.toSectionUIItems
import com.vlv.configuration.domain.usecase.SettingsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val useCase: SettingsUseCase
) : ViewModel() {

    private val _config: MutableResponseLiveData<List<SectionUIItem>> = MutableResponseLiveData()

    val config: ResponseLiveData<List<SectionUIItem>>
        get() = _config

    fun config() = useCase
        .configData()
        .apply {
            _config.postLoading()
        }
        .responseLiveData
        .map {
            it.toSectionUIItems()
        }
        .onError {
            _config.postError(it)
        }
        .onNext {
            _config.postData(it)
        }

    fun setData(sectionUIItem: SectionUIItem) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                val data = _config.value?.data ?: return@launch

                _config.postData(
                    data.map {
                        if(it.id == sectionUIItem.id) {
                            useCase.setConfigValue(
                                sectionUIItem.settingsOption ?: return@launch,
                                sectionUIItem.data ?: return@launch,
                                sectionUIItem.type.toConfigItemType()
                            )
                            sectionUIItem
                        }
                        else it
                    }
                )
            }
        }
    }

    private fun getConfigValue(key: String) = useCase.getConfigValue(key)

    fun setConfigValue(key: String, value: Boolean) {
        viewModelScope.launch {
            useCase.setConfigValue(key, value)
        }
    }

}