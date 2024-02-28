package com.vlv.configuration.presentation.ui

import com.vlv.configuration.domain.usecase.SettingsUseCase
import com.vlv.configuration.presentation.ConfigurationInitializer
import com.vlv.test.KoinRule
import io.mockk.mockk
import org.junit.Rule
import org.junit.Test
import org.koin.dsl.module

val myModule = module {
    single { mockk<SettingsUseCase>(relaxed = true) }
}

class SettingsActivityTest {

    @JvmField
    @Rule
    val koinRule = KoinRule(
        listOf(myModule),
        ConfigurationInitializer::class.java
    )

    @Test
    fun withLoading_shouldShowLoadingState() {
        settingsActivity {
            withLoading()
        } check {
            loadingStateDisplayed()
        }
    }

    @Test
    fun withError_shouldShowErrorState() {
        settingsActivity {
            withError()
        } check {
            errorStateDisplayed()
        }
    }

    @Test
    fun withError_andClickTryAgain_shouldLoadConfigAgain() {
        settingsActivity {
            withError()
        } launch {
            clickTryAgain()
        } check {
            configLoaded(quantity = 2)
        }
    }

    @Test
    fun withDataEmpty_shouldShowErrorState() {
        settingsActivity {
            withDataEmpty()
        } check {
            errorStateDisplayed()
        }
    }

    @Test
    fun withData_shouldDisplayedConfigurationContent() {
        settingsActivity {
            withDataDefault()
        } check {
            dataStateDisplayed()
        }
    }

    @Test
    fun withDataAdultContentOptionDisable_andClickOption_shouldShowOptionEnabled() {
        settingsActivity {
            withDataAdultContentDisable()
        } launch {
            clickAdultContentOption()
        } check {
            adultContentOption(enabled = true)
        }
    }

    @Test
    fun withDataAdultContentOptionEnabled_andClickOption_shouldShowOptionDisabled() {
        settingsActivity {
            withDataAdultContentEnabled()
        } launch {
            clickAdultContentOption()
        } check {
            adultContentOption(enabled = false)
        }
    }

    @Test
    fun withData_andClickLanguageOption_shouldOpenActionSheetLanguages() {
        settingsActivity {
            withDataDefault()
        } launch {
            clickLanguageOption()
        } check {
            actionSheetLanguagesDisplayed()
        }
    }

    @Test
    fun withActionSheetLanguages_andSelectNewLanguage_shouldDisplayedNewLanguageChosen() {
        settingsActivity {
            withDataDefault()
        } launch {
            clickLanguageOption()
            selectLanguageDutchOption()
        } check {
            languageSelectedIsDutch()
        }
    }

    @Test
    fun withData_andClickLogoImageOption_shouldOpenActionSheetLogoImage() {
        settingsActivity {
            withDataDefault()
        } launch {
            clickLogoOption()
        } check {
            actionSheetLogoDisplayed()
        }
    }
}