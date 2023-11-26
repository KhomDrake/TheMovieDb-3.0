package com.vlv.configuration.presentation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vlv.bondsmith.bondsmith
import com.vlv.bondsmith.data.Response
import com.vlv.configuration.data.SettingsDataUI
import com.vlv.configuration.data.SettingsSectionItemsUI
import com.vlv.configuration.data.SettingsSectionUI
import com.vlv.configuration.domain.model.SettingsData
import com.vlv.configuration.domain.usecase.SettingsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val useCase: SettingsUseCase
) : ViewModel() {

    val state = MutableStateFlow<Response<SettingsDataUI>>(Response())

    fun settings() {
        viewModelScope.launch(Dispatchers.IO) {
            bondsmith<SettingsDataUI>()
                .request {
                    val config = useCase.newConfig()
                    SettingsDataUI(config)
                }
                .execute()
                .responseStateFlow
                .collectLatest {
                    state.emit(it)
                }
        }
    }

    fun setData(section: Int, selected: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                val data = state.value.data ?: return@launch

                val newData = data.copy(
                    sections = data.sections.map { sections: SettingsSectionUI ->
                        sections.copy(
                            sectionData = sections.sectionData.map { settingsSectionItemsUI ->
                                if(settingsSectionItemsUI.id == section) {
                                    settingsSectionItemsUI.copy(
                                        selectedItem = settingsSectionItemsUI.items.first {
                                            it.id == selected
                                        }
                                    )
                                } else settingsSectionItemsUI
                            }
                        )
                    }
                )

                state.emit(
                    state.value.copy(
                        data = newData
                    )
                )
            }
        }
    }

}