package com.vlv.configuration.presentation.ui

import com.vlv.configuration.domain.model.SettingOption
import com.vlv.configuration.domain.usecase.SettingsUseCase
import com.vlv.test.ComposeBaseTest
import com.vlv.test.KoinRule
import io.mockk.mockk
import org.junit.Rule
import org.junit.Test
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

private val myModule = module {
    single { mockk<SettingsUseCase>(relaxed = true) }
    viewModel { SettingsViewModel(mockk(relaxed = true), get()) }
}

class SettingsActivityTest : ComposeBaseTest() {

    @JvmField
    @Rule
    val koinRule = KoinRule(
        listOf(myModule)
    )

    @Test
    fun withDefaultData_shouldShowDefaultData() {
        settingsContent(composeRule) {
            withData()
        } check {
            defaultDataDisplayed()
        }
    }

    @Test
    fun withDefaultData_clickClickLanguages_shouldOpenActionSheetLanguages() {
        settingsContent(composeRule) {
            withData()
        } launch {
            clickLanguages()
        } check {
            actionSheetLanguages()
        }
    }

    @Test
    fun withDefaultData_clickClickRegion_shouldOpenActionSheetRegion() {
        settingsContent(composeRule) {
            withData()
        } launch {
            clickRegion()
        } check {
            actionSheetRegion()
        }
    }

    @Test
    fun withDataEmpty_shouldShowErrorState() {
        settingsContent(composeRule) {
            withDataEmpty()
        } check {
            errorStateDisplayed()
        }
    }

    @Test
    fun withError_shouldShowErrorState() {
        settingsContent(composeRule) {
            withError()
        } check {
            errorStateDisplayed()
        }
    }

    @Test
    fun withError_clickTryAgain_shouldRequestSettingsAgain() {
        settingsContent(composeRule) {
            withError()
        } launch {
            clickTryAgain()
        } check {
            requestSettingsTwice()
        }
    }

    @Test
    fun withSwitchOff_clickSwitch_shouldChangeSwitchToOn() {
        settingsContent(composeRule) {
            withData()
        } launch {
            clickSwitch()
        } check {
            switchOn()
            configSwitchChanged(
                SettingOption.ADULT_CONTENT, true
            )
        }
    }

    @Test
    fun withSwitchOn_clickSwitch_shouldChangeSwitchToOff() {
        settingsContent(composeRule) {
            withData(switch = true)
        } launch {
            clickSwitch()
        } check {
            switchOff()
            configSwitchChanged(
                SettingOption.ADULT_CONTENT, false
            )
        }
    }

    @Test
    fun withActionSheetRegion_clickDifferentRegion_shouldChangeData() {
        settingsContent(composeRule) {
            withData()
        } launch {
            clickRegion()
            clickDifferentRegion()
            clickConfirmButton()
        } check {
            configValueRegionChanged()
            differentRegionDisplayed()
        }
    }

    @Test
    fun withActionSheetLanguage_clickDifferentLanguage_shouldChangeData() {
        settingsContent(composeRule) {
            withData()
        } launch {
            clickLanguages()
            clickDifferentLanguage()
            clickConfirmButton()
        } check {
            configValueLanguageChanged()
            differentLanguageDisplayed()
        }
    }

}