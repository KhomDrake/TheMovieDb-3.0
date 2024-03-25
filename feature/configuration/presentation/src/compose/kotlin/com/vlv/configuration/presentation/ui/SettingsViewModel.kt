package com.vlv.configuration.presentation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.khomdrake.request.data.flow.MutableResponseStateFlow
import br.com.khomdrake.request.data.flow.ResponseStateFlow
import br.com.khomdrake.request.data.flow.asResponseStateFlow
import com.vlv.configuration.data.SectionUIItem
import com.vlv.configuration.data.toConfigItemType
import com.vlv.configuration.data.toSectionUIItems
import com.vlv.configuration.domain.usecase.SettingsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val useCase: SettingsUseCase
) : ViewModel() {

    private val _state = MutableResponseStateFlow<List<SectionUIItem>>()

    val state: ResponseStateFlow<List<SectionUIItem>>
        get() = _state.asResponseStateFlow()

    fun settings() {
        viewModelScope.launch(Dispatchers.IO) {
            useCase.configData()
                .responseStateFlow
                .mapData {
                    it?.toSectionUIItems() ?: listOf()
                }
                .collectLatest {
                    _state.emit(it)
                }
        }
    }

    fun setData(sectionUIItem: SectionUIItem) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                val data = _state.value.data ?: return@launch

                _state.emit(
                    _state.value.copy(
                        data = data.map {
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
                )
            }
        }
    }

}