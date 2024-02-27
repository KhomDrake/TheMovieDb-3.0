package com.vlv.configuration.presentation.ui

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.platform.app.InstrumentationRegistry
import com.vlv.bondsmith.bondsmith
import com.vlv.common.route.toSettings
import com.vlv.configuration.domain.model.ConfigDataItemList
import com.vlv.configuration.domain.model.ConfigDataList
import com.vlv.configuration.domain.model.ConfigItemType
import com.vlv.configuration.domain.model.Section
import com.vlv.configuration.domain.model.SectionConfig
import com.vlv.configuration.domain.model.SectionsData
import com.vlv.configuration.domain.model.SettingOption
import com.vlv.configuration.domain.usecase.SettingsUseCase
import com.vlv.configuration.presentation.R
import com.vlv.test.Check
import com.vlv.test.Launch
import com.vlv.test.Setup
import com.vlv.test.checkViewOnRecyclerViewPosition
import com.vlv.test.clickIgnoreConstraint
import com.vlv.test.clickOnRecyclerViewInsideItem
import com.vlv.test.clickOnRecyclerViewItem
import com.vlv.test.hasText
import com.vlv.test.isDisplayed
import com.vlv.test.isNotDisplayed
import io.mockk.every
import io.mockk.verify
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

fun SettingsActivityTest.settingsActivity(func: SettingsActivitySetup.() -> Unit) =
    SettingsActivitySetup().apply(func)

class SettingsActivitySetup : Setup<SettingsActivityLaunch, SettingsActivityCheck>, KoinComponent {

    private val useCase: SettingsUseCase by inject()

    override fun createLaunch(): SettingsActivityLaunch {
        return SettingsActivityLaunch()
    }

    override fun createCheck(): SettingsActivityCheck {
        return SettingsActivityCheck()
    }

    override fun setupLaunch() {
        ActivityScenario.launch<SettingsActivity>(
            InstrumentationRegistry.getInstrumentation().context.toSettings()
        )
    }

    fun withLoading() {
        every {
            useCase.configData()
        } returns bondsmith<SectionsData>()
            .apply {
                setLoading()
            }
    }

    fun withError() {
        every {
            useCase.configData()
        } returns bondsmith<SectionsData>()
            .apply {
                setError(Throwable())
            }
    }

    fun withDataEmpty() {
        every {
            useCase.configData()
        } returns bondsmith<SectionsData>()
            .apply {
                setData(
                    SectionsData(
                        listOf()
                    )
                )
            }
    }

    fun withDataDefault(adultContentEnabled: Boolean = false) {
        every {
            useCase.configData()
        } returns bondsmith<SectionsData>()
            .apply {
                setData(
                    SectionsData(
                        listOf(
                            Section(
                                "General",
                                listOf(
                                    SectionConfig(
                                        SettingOption.ADULT_CONTENT,
                                        ConfigItemType.SWITCH,
                                        "Show adult content",
                                        "If enabled, adult content will show up in search or lists",
                                        adultContentEnabled
                                    ),
                                    SectionConfig(
                                        SettingOption.LANGUAGE,
                                        ConfigItemType.LIST,
                                        "Languages",
                                        "Chosen language:",
                                        ConfigDataList(
                                            "Languages",
                                            null,
                                            selectedItem = ConfigDataItemList(
                                                "English", "en", id = 1
                                            ),
                                            items = listOf(
                                                ConfigDataItemList(
                                                    "Dutch", "de", id = 2
                                                ),
                                                ConfigDataItemList(
                                                    "English", "en", id = 1
                                                ),
                                                ConfigDataItemList(
                                                    "Japanese", "ja", id = 3
                                                ),
                                                ConfigDataItemList(
                                                    "Portuguese", "pt", id = 4
                                                )
                                            )
                                        )
                                    ),
                                    SectionConfig(
                                        SettingOption.LOGO,
                                        ConfigItemType.LIST,
                                        "Logo",
                                        "Chosen definition:",
                                        ConfigDataList(
                                            "Images quality",
                                            null,
                                            selectedItem = ConfigDataItemList(
                                                "Medium low", "en", id = 1
                                            ),
                                            items = listOf(
                                                ConfigDataItemList(
                                                    "Low", "de", id = 2
                                                ),
                                                ConfigDataItemList(
                                                    "Medium low", "en", id = 1
                                                ),
                                                ConfigDataItemList(
                                                    "Medium", "ja", id = 3
                                                ),
                                                ConfigDataItemList(
                                                    "High", "pt", id = 4
                                                )
                                            )
                                        )
                                    )
                                )
                            )
                        )
                    )
                )
            }
    }

    fun withDataAdultContentEnabled() {
        withDataDefault(adultContentEnabled = true)
    }

    fun withDataAdultContentDisable() {
        withDataDefault(adultContentEnabled = false)
    }

}

class SettingsActivityLaunch : Launch<SettingsActivityCheck> {
    override fun createCheck(): SettingsActivityCheck {
        return SettingsActivityCheck()
    }

    fun clickTryAgain() {
        com.vlv.imperiya.core.R.id.small_warning_try_again_button.clickIgnoreConstraint()
    }

    fun clickAdultContentOption() {
        R.id.items.clickOnRecyclerViewInsideItem(
            1,
            R.id.switch_settings
        )
    }

    fun clickLanguageOption() {
        R.id.items.clickOnRecyclerViewItem(2)
    }

    fun clickLogoOption() {
        R.id.items.clickOnRecyclerViewItem(3)
    }

    fun selectLanguageDutchOption() {
        com.vlv.imperiya.core.R.id.action_sheet_items.clickOnRecyclerViewItem(0)
        com.vlv.imperiya.core.R.id.confirm_button.clickIgnoreConstraint()
    }

}

class SettingsActivityCheck : Check, KoinComponent {

    private val useCase: SettingsUseCase by inject()

    fun loadingStateDisplayed() {
        R.id.shimmer_settings.isDisplayed()
        R.id.error_state_settings.isNotDisplayed()
        R.id.items.isNotDisplayed()
        R.id.toolbar.isDisplayed()
    }

    fun errorStateDisplayed() {
        R.id.shimmer_settings.isNotDisplayed()
        R.id.error_state_settings.isDisplayed()
        R.id.items.isNotDisplayed()
        R.id.toolbar.isNotDisplayed()
    }

    fun configLoaded(quantity: Int) {
        verify(exactly = quantity) {
            useCase.configData()
        }
    }

    fun dataStateDisplayed() {
        R.id.shimmer_settings.isNotDisplayed()
        R.id.error_state_settings.isNotDisplayed()
        R.id.items.isDisplayed()
        R.id.toolbar.isDisplayed()

        R.id.items.apply {
            checkViewOnRecyclerViewPosition(
                0,
                ViewMatchers.withText("General"),
                R.id.title_configuration
            )

            checkViewOnRecyclerViewPosition(
                1,
                ViewMatchers.withText("Show adult content"),
                R.id.settings_title
            )
            checkViewOnRecyclerViewPosition(
                1,
                ViewMatchers.withText("If enabled, adult content will show up in search or lists"),
                R.id.settings_body
            )

            checkViewOnRecyclerViewPosition(
                2,
                ViewMatchers.withText("Languages"),
                R.id.settings_title
            )
            checkViewOnRecyclerViewPosition(
                2,
                ViewMatchers.withText("Chosen language: English"),
                R.id.settings_body
            )

            checkViewOnRecyclerViewPosition(
                3,
                ViewMatchers.withText("Logo"),
                R.id.settings_title
            )
            checkViewOnRecyclerViewPosition(
                3,
                ViewMatchers.withText("Chosen definition: Medium low"),
                R.id.settings_body
            )
        }
    }

    fun adultContentOption(enabled: Boolean) {
       R.id.items.checkViewOnRecyclerViewPosition(
           1,
           if(enabled) ViewMatchers.isChecked() else ViewMatchers.isNotChecked(),
           R.id.switch_settings
       )
    }

    fun actionSheetLanguagesDisplayed() {
        com.vlv.imperiya.core.R.id.action_sheet_title.isDisplayed()
        com.vlv.imperiya.core.R.id.action_sheet_title.hasText("Languages")

        com.vlv.imperiya.core.R.id.action_sheet_items.apply {
            isDisplayed()
            checkViewOnRecyclerViewPosition(
                0,
                ViewMatchers.withText("Dutch"),
                com.vlv.imperiya.core.R.id.action_sheet_item_title
            )
            checkViewOnRecyclerViewPosition(
                0,
                ViewMatchers.isNotChecked(),
                com.vlv.imperiya.core.R.id.action_sheet_item_checkbox
            )

            checkViewOnRecyclerViewPosition(
                1,
                ViewMatchers.withText("English"),
                com.vlv.imperiya.core.R.id.action_sheet_item_title
            )
            checkViewOnRecyclerViewPosition(
                1,
                ViewMatchers.isChecked(),
                com.vlv.imperiya.core.R.id.action_sheet_item_checkbox
            )
        }
    }

    fun languageSelectedIsDutch() {
        R.id.items.apply {
            checkViewOnRecyclerViewPosition(
                2,
                ViewMatchers.withText("Languages"),
                R.id.settings_title
            )
            checkViewOnRecyclerViewPosition(
                2,
                ViewMatchers.withText("Chosen language: Dutch"),
                R.id.settings_body
            )
        }
    }

    fun actionSheetLogoDisplayed() {
        com.vlv.imperiya.core.R.id.action_sheet_title.isDisplayed()
        com.vlv.imperiya.core.R.id.action_sheet_title.hasText("Images quality")

        com.vlv.imperiya.core.R.id.action_sheet_items.apply {
            isDisplayed()
            checkViewOnRecyclerViewPosition(
                0,
                ViewMatchers.withText("Low"),
                com.vlv.imperiya.core.R.id.action_sheet_item_title
            )
            checkViewOnRecyclerViewPosition(
                0,
                ViewMatchers.isNotChecked(),
                com.vlv.imperiya.core.R.id.action_sheet_item_checkbox
            )

            checkViewOnRecyclerViewPosition(
                1,
                ViewMatchers.withText("Medium low"),
                com.vlv.imperiya.core.R.id.action_sheet_item_title
            )
            checkViewOnRecyclerViewPosition(
                1,
                ViewMatchers.isChecked(),
                com.vlv.imperiya.core.R.id.action_sheet_item_checkbox
            )
        }
    }

}

