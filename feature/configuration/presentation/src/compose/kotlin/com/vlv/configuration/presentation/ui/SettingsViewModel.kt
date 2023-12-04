package com.vlv.configuration.presentation.ui

import android.content.res.Resources
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vlv.bondsmith.bondsmith
import com.vlv.bondsmith.data.Response
import com.vlv.bondsmith.emitError
import com.vlv.bondsmith.mapData
import com.vlv.configuration.data.SectionUIItem
import com.vlv.configuration.data.toConfigItemType
import com.vlv.configuration.data.toSectionUIItems
import com.vlv.configuration.domain.model.SectionsData
import com.vlv.configuration.domain.usecase.SettingsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val resources: Resources,
    private val useCase: SettingsUseCase
) : ViewModel() {

    val state = MutableStateFlow<Response<List<SectionUIItem>>>(Response())

    fun settings() {
        viewModelScope.launch(Dispatchers.IO) {
            bondsmith<SectionsData>()
                .request {
                    useCase.configData(resources)
                }
                .execute()
                .responseStateFlow
                .mapData {
                    it?.toSectionUIItems()
                }
                .catch {
                    state.emitError(it)
                }
                .collectLatest {
                    state.emit(it)
                }
        }
    }

    fun setData(sectionUIItem: SectionUIItem) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                val data = state.value.data ?: return@launch

                state.emit(
                    state.value.copy(
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