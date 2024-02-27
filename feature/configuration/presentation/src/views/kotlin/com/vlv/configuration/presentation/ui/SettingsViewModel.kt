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
        .responseLiveData
        .apply {
            _config.postLoading()
        }
        .map {
            it.toSectionUIItems()
        }
        .onError {
            _config.postError(it)
        }
        .onNext {
            if(it.isEmpty()) _config.postError(Throwable())
            else _config.postData(it)
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

}