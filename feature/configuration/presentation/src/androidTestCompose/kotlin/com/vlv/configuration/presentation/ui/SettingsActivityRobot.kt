package com.vlv.configuration.presentation.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsOff
import androidx.compose.ui.test.assertIsOn
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.vlv.configuration.domain.model.ConfigDataItemList
import com.vlv.configuration.domain.model.ConfigDataList
import com.vlv.configuration.domain.model.ConfigItemType
import com.vlv.configuration.domain.model.Section
import com.vlv.configuration.domain.model.SectionConfig
import com.vlv.configuration.domain.model.SectionsData
import com.vlv.configuration.domain.model.SettingOption
import com.vlv.configuration.domain.usecase.SettingsUseCase
import com.vlv.core.test.smallWarningView
import com.vlv.imperiya.core.ui.preview.BackgroundPreview
import com.vlv.imperiya.core.ui.theme.TheMovieDbAppTheme
import com.vlv.test.Check
import com.vlv.test.Launch
import com.vlv.test.Setup
import io.mockk.coEvery
import io.mockk.coVerify
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

fun SettingsActivityTest.settingsContent(
    composeContentTestRule: ComposeContentTestRule,
    func: SettingsActivitySetup.() -> Unit
) =
    SettingsActivitySetup(composeContentTestRule).apply(func)

class SettingsActivitySetup(
    private val composeRule: ComposeContentTestRule
) : Setup<SettingsActivityLaunch, SettingsActivityCheck>, KoinComponent {

    private val useCase: SettingsUseCase by inject()

    override fun createCheck(): SettingsActivityCheck {
        return SettingsActivityCheck(composeRule)
    }

    override fun createLaunch(): SettingsActivityLaunch {
        return SettingsActivityLaunch(composeRule)
    }

    fun withError() {
        coEvery {
            useCase.configData(any())
        } throws Exception()
    }

    fun withDataEmpty() {
        coEvery {
            useCase.configData(any())
        } returns SectionsData(
            listOf()
        )
    }

    fun withData(switch: Boolean = false) {
        coEvery {
            useCase.configData(any())
        } returns SectionsData(
            listOf(
                Section(
                    "General",
                    listOf(
                        SectionConfig(
                            SettingOption.ADULT_CONTENT,
                            ConfigItemType.SWITCH,
                            title = "Test",
                            description = "Show adult content",
                            data = switch,
                            id = 2
                        ),
                        SectionConfig(
                            SettingOption.LANGUAGE,
                            ConfigItemType.LIST,
                            title = "Languages",
                            description = "Language chosen:",
                            data = ConfigDataList(
                                "kajsdka",
                                "ldkasjlka",
                                selectedItem = ConfigDataItemList(
                                    title = "English",
                                    value = "en",
                                    id = 4
                                ),
                                items = listOf(
                                    ConfigDataItemList(
                                        title = "English",
                                        value = "en",
                                        id = 4
                                    ),
                                    ConfigDataItemList(
                                        title = "Portuguese Br",
                                        value = "pt-br",
                                        id = 5
                                    ),
                                    ConfigDataItemList(
                                        title = "Portuguese Portugual",
                                        value = "pt",
                                        id = 6
                                    ),
                                    ConfigDataItemList(
                                        title = "English Australia",
                                        value = "en-AU",
                                        id = 7
                                    ),
                                )
                            ),
                            id = 3
                        ),
                        SectionConfig(
                            SettingOption.REGION,
                            ConfigItemType.LIST,
                            title = "Region",
                            description = "Region chosen:",
                            data = ConfigDataList(
                                "kajsdka",
                                "ldkasjlka",
                                selectedItem = ConfigDataItemList(
                                    title = "Angola",
                                    value = "en",
                                    id = 11
                                ),
                                items = listOf(
                                    ConfigDataItemList(
                                        title = "Angola",
                                        value = "an",
                                        id = 11
                                    ),
                                    ConfigDataItemList(
                                        title = "Argentina",
                                        value = "ar",
                                        id = 12
                                    ),
                                    ConfigDataItemList(
                                        title = "Australia",
                                        value = "au",
                                        id = 13
                                    ),
                                    ConfigDataItemList(
                                        title = "Austria",
                                        value = "aus",
                                        id = 14
                                    ),
                                )
                            ),
                            id = 10
                        )
                    ),
                    id = 1
                )
            )
        )
    }

    override fun setupLaunch() {
        coEvery {
            useCase.setConfigValue(
                SettingOption.ADULT_CONTENT,
                true,
                ConfigItemType.SWITCH
            )
        } returns Unit

        coEvery {
            useCase.setConfigValue(
                SettingOption.LANGUAGE,
                "pt-br",
                ConfigItemType.LIST
            )
        } returns Unit

        coEvery {
            useCase.setConfigValue(
                SettingOption.REGION,
                "au",
                ConfigItemType.LIST
            )
        } returns Unit

        composeRule.setContent {
            TheMovieDbAppTheme {
                BackgroundPreview(modifier = Modifier.fillMaxSize()) {
                    SettingsScreen {

                    }
                }
            }
        }

        composeRule.waitForIdle()
    }

}

class SettingsActivityLaunch(
    private val composeRule: ComposeContentTestRule
) : Launch<SettingsActivityCheck> {

    override fun createCheck(): SettingsActivityCheck {
        return SettingsActivityCheck(composeRule)
    }

    fun clickLanguages() {
        composeRule
            .onNodeWithTag("3", useUnmergedTree = true)
            .performClick()
    }

    fun clickRegion() {
        composeRule
            .onNodeWithTag("10", useUnmergedTree = true)
            .performClick()
    }

    fun clickSwitch() {
        composeRule
            .onNodeWithTag("2", useUnmergedTree = true)
            .onChildren()
            .get(1)
            .performClick()
    }

    fun clickDifferentLanguage() {
        composeRule
            .onNodeWithTag("5", useUnmergedTree = true)
            .performClick()
    }

    fun clickDifferentRegion() {
        composeRule
            .onNodeWithTag("13", useUnmergedTree = true)
            .performClick()
    }

    fun clickConfirmButton() {
        composeRule
            .onNodeWithTag("BottomSheetButton", useUnmergedTree = true)
            .performClick()
    }

    fun clickTryAgain() {
        composeRule.apply {
            onNodeWithTag("SettingsError", useUnmergedTree = true)
                .onChildren()
                .get(3)
                .performClick()
        }
    }
}

class SettingsActivityCheck(
    private val composeRule: ComposeContentTestRule
) : Check, KoinComponent {

    private val useCase: SettingsUseCase by inject()

    fun defaultDataDisplayed() {
        composeRule.apply {

            onNodeWithTag("1", useUnmergedTree = true)
                .assertTextEquals("General")
                .assertIsDisplayed()

            onNodeWithTag("2", useUnmergedTree = true)
                .onChildren()
                .get(0)
                .assertTextEquals("Show adult content")

            onNodeWithTag("2", useUnmergedTree = true)
                .onChildren()
                .get(1)
                .assertIsOff()

            onNodeWithTag("3", useUnmergedTree = true)
                .onChildren()
                .get(0)
                .assertTextEquals("Languages")

            onNodeWithTag("3", useUnmergedTree = true)
                .onChildren()
                .get(1)
                .assertTextEquals("Language chosen: English")
        }
    }

    fun errorStateDisplayed() {
        composeRule
            .smallWarningView("SettingsError")
            .isDisplayed()
            .withTitle("Failed to load")
            .withBody("Check your internet connection, wait a few moments and click in load again")
            .withButtonLink("Load again")
    }

    fun requestSettingsTwice() {
        coVerify(exactly = 2) {
            useCase.configData(any())
        }
    }

    fun configSwitchChanged(settingOption: SettingOption, value: Boolean) {
        coVerify {
            useCase.setConfigValue(
                settingOption,
                value,
                ConfigItemType.SWITCH
            )
        }
    }

    fun switchOff() {
        composeRule
            .onNodeWithTag("2", useUnmergedTree = true)
            .onChildren()
            .get(1)
            .assertIsOff()
    }

    fun switchOn() {
        composeRule
            .onNodeWithTag("2", useUnmergedTree = true)
            .onChildren()
            .get(1)
            .assertIsOn()
    }

    fun configValueRegionChanged() {
        coVerify {
            useCase.setConfigValue(
                SettingOption.REGION,
                ConfigDataList(
                    "kajsdka",
                    "ldkasjlka",
                    selectedItem = ConfigDataItemList(
                        title = "Australia",
                        value = "au",
                        id = 13
                    ),
                    items = listOf(
                        ConfigDataItemList(
                            title = "Angola",
                            value = "an",
                            id = 11
                        ),
                        ConfigDataItemList(
                            title = "Argentina",
                            value = "ar",
                            id = 12
                        ),
                        ConfigDataItemList(
                            title = "Australia",
                            value = "au",
                            id = 13
                        ),
                        ConfigDataItemList(
                            title = "Austria",
                            value = "aus",
                            id = 14
                        ),
                    )
                ),
                ConfigItemType.LIST
            )
        }
    }

    fun configValueLanguageChanged() {
        coVerify {
            useCase.setConfigValue(
                SettingOption.LANGUAGE,
                ConfigDataList(
                    "kajsdka",
                    "ldkasjlka",
                    selectedItem = ConfigDataItemList(
                        title = "Portuguese Br",
                        value = "pt-br",
                        id = 5
                    ),
                    items = listOf(
                        ConfigDataItemList(
                            title = "English",
                            value = "en",
                            id = 4
                        ),
                        ConfigDataItemList(
                            title = "Portuguese Br",
                            value = "pt-br",
                            id = 5
                        ),
                        ConfigDataItemList(
                            title = "Portuguese Portugual",
                            value = "pt",
                            id = 6
                        ),
                        ConfigDataItemList(
                            title = "English Australia",
                            value = "en-AU",
                            id = 7
                        ),
                    )
                ),
                ConfigItemType.LIST
            )
        }
    }

    fun differentLanguageDisplayed() {
        composeRule
            .onNodeWithTag("3", useUnmergedTree = true)
            .onChildren()
            .get(1)
            .assertTextEquals("Language chosen: Portuguese Br")
    }

    fun differentRegionDisplayed() {
        composeRule
            .onNodeWithTag("10", useUnmergedTree = true)
            .onChildren()
            .get(1)
            .assertTextEquals("Region chosen: Australia")
    }

    fun actionSheetLanguages() {
        composeRule
            .onNodeWithTag("4", useUnmergedTree = true)
            .onChildren()
            .apply {
                get(0).assertTextEquals("English")
                get(1).assertIsOn()
            }

        composeRule
            .onNodeWithTag("5", useUnmergedTree = true)
            .onChildren()
            .apply {
                get(0).assertTextEquals("Portuguese Br")
                get(1).assertIsOff()
            }
    }

    fun actionSheetRegion() {
        composeRule
            .onNodeWithTag("11", useUnmergedTree = true)
            .onChildren()
            .apply {
                get(0).assertTextEquals("Angola")
                get(1).assertIsOn()
            }

        composeRule
            .onNodeWithTag("12", useUnmergedTree = true)
            .onChildren()
            .apply {
                get(0).assertTextEquals("Argentina")
                get(1).assertIsOff()
            }

        composeRule
            .onNodeWithTag("13", useUnmergedTree = true)
            .onChildren()
            .apply {
                get(0).assertTextEquals("Australia")
                get(1).assertIsOff()
            }
    }

}