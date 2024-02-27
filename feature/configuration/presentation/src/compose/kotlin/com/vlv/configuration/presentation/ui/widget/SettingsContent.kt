package com.vlv.configuration.presentation.ui.widget

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.vlv.bondsmith.data.Response
import com.vlv.bondsmith.data.ResponseStatus
import com.vlv.common.route.RouteNavigation
import com.vlv.common.route.ScreenRoute
import com.vlv.configuration.data.SectionUIItem
import com.vlv.configuration.data.SectionUIType
import com.vlv.configuration.domain.model.ConfigDataItemList
import com.vlv.configuration.domain.model.ConfigDataList
import com.vlv.configuration.domain.model.SettingsAction
import com.vlv.configuration.presentation.ui.SettingsViewModel
import com.vlv.imperiya.core.ui.components.SmallWarningView
import com.vlv.imperiya.core.ui.preview.BackgroundPreview
import com.vlv.imperiya.core.ui.theme.TheMovieDbAppTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun SettingsContent(
    paddingValues: PaddingValues,
    routeNavigation: RouteNavigation,
    viewModel: SettingsViewModel = koinViewModel()
) {
    val data by viewModel.state.collectAsState()

    LaunchedEffect(key1 = null, block = {
        viewModel.settings()
    })

    SettingsState(
        data = data,
        paddingValues = paddingValues,
        onConfirmChangeItem = { item ->
            viewModel.setData(item)
            when(item.settingsOption?.action) {
                SettingsAction.RE_LAUNCH_APP -> {
                    routeNavigation.invoke(ScreenRoute.RESTART_THE_APP, null)
                }
                else -> Unit
            }
        },
        onTryAgain = {
            viewModel.settings()
        }
    )
}

@Composable
fun SettingsState(
    data: Response<List<SectionUIItem>>,
    paddingValues: PaddingValues,
    onConfirmChangeItem: (SectionUIItem) -> Unit,
    onTryAgain: () -> Unit
) {
    when(data.state) {
        ResponseStatus.SUCCESS -> {
            val settingsData = data.data
            if(!settingsData.isNullOrEmpty()) {
                SettingsItems(
                    items = settingsData,
                    paddingValues = paddingValues,
                    onConfirmChangeItem = onConfirmChangeItem
                )
            } else {
                SettingsError(paddingValues, onTryAgain)
            }
        }
        ResponseStatus.ERROR -> {
            SettingsError(paddingValues, onTryAgain)
        }
        ResponseStatus.LOADING -> {
            SettingsShimmer(
                modifier = Modifier
                    .padding(
                        top = paddingValues.calculateTopPadding(),
                        start = 16.dp,
                        end = 16.dp
                    )
            )
        }
        else -> Unit
    }
}

@Composable
fun SettingsError(
    paddingValues: PaddingValues,
    onTryAgain: () -> Unit
) {
    SmallWarningView(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = paddingValues.calculateTopPadding() + 16.dp,
                start = 16.dp,
                end = 16.dp,
            )
            .testTag("SettingsError"),
        title = stringResource(id = com.vlv.ui.R.string.common_error_title),
        body = stringResource(id = com.vlv.ui.R.string.common_error_description_load),
        linkActionText = stringResource(id = com.vlv.ui.R.string.common_error_button_load),
        onClickLink = onTryAgain
    )
}

@PreviewLightDark
@Composable
fun SettingsStateSuccessPreview() {
    TheMovieDbAppTheme {
        BackgroundPreview {
            SettingsState(
                data = Response(
                    state = ResponseStatus.SUCCESS,
                    data = listOf(
                        SectionUIItem(
                            SectionUIType.HEADER,
                            1,
                            null,
                            "General"
                        ),
                        SectionUIItem(
                            SectionUIType.SWITCH,
                            2,
                            true,
                            "General",
                            "Show adult content"
                        ),
                        SectionUIItem(
                            SectionUIType.LIST,
                            3,
                            ConfigDataList(
                                "asdas",
                                "asdsadsadsa",
                                ConfigDataItemList(
                                    "German",
                                    "German"
                                ),
                                listOf()
                            ),
                            "Languages",
                            "Language chosen:"
                        ),
                    )
                ),
                paddingValues = PaddingValues(
                    top = 16.dp
                ),
                onConfirmChangeItem = {},
                onTryAgain = {}
            )

        }
    }
}

@PreviewLightDark
@Composable
fun SettingsStateSuccessEmptyPreview() {
    TheMovieDbAppTheme {
        BackgroundPreview {
            SettingsState(
                data = Response(
                    state = ResponseStatus.SUCCESS,
                    data = listOf()
                ),
                paddingValues = PaddingValues(
                    top = 16.dp
                ),
                onConfirmChangeItem = {},
                onTryAgain = {}
            )
        }
    }
}

@PreviewLightDark
@Composable
fun SettingsStateSuccessNullPreview() {
    TheMovieDbAppTheme {
        BackgroundPreview {
            SettingsState(
                data = Response(
                    state = ResponseStatus.SUCCESS,
                    data = null
                ),
                paddingValues = PaddingValues(
                    top = 16.dp
                ),
                onConfirmChangeItem = {},
                onTryAgain = {}
            )
        }
    }
}

@PreviewLightDark
@Composable
fun SettingsStateErrorPreview() {
    TheMovieDbAppTheme {
        BackgroundPreview {
            SettingsState(
                data = Response(
                    state = ResponseStatus.ERROR,
                    error = Throwable()
                ),
                paddingValues = PaddingValues(
                    top = 16.dp
                ),
                onConfirmChangeItem = {},
                onTryAgain = {}
            )
        }
    }
}

@PreviewLightDark
@Composable
fun SettingsStateLoadingPreview() {
    TheMovieDbAppTheme {
        BackgroundPreview {
            SettingsState(
                data = Response(
                    state = ResponseStatus.LOADING
                ),
                paddingValues = PaddingValues(
                    top = 16.dp
                ),
                onConfirmChangeItem = {},
                onTryAgain = {}
            )
        }
    }
}