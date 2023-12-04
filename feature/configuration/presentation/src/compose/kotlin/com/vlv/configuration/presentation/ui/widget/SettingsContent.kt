package com.vlv.configuration.presentation.ui.widget

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.vlv.bondsmith.data.ResponseStatus
import com.vlv.common.ui.shimmer.CarouselShimmer
import com.vlv.configuration.presentation.ui.SettingsViewModel
import com.vlv.imperiya.core.ui.components.SmallWarningView
import org.koin.androidx.compose.koinViewModel

@Composable
fun SettingsContent(
    paddingValues: PaddingValues,
    viewModel: SettingsViewModel = koinViewModel()
) {
    val data by viewModel.state.collectAsState()

    LaunchedEffect(key1 = 2, block = {
        viewModel.settings()
    })

    when(data.state) {
        ResponseStatus.SUCCESS -> {
            val items = data.data ?: return
            SettingsItems(
                items = items,
                paddingValues = paddingValues,
                onConfirmChangeItem = { item ->
                    viewModel.setData(item)
                }
            )
        }
        ResponseStatus.ERROR -> {
            SmallWarningView(
                modifier = Modifier
                    .padding(top = paddingValues.calculateTopPadding()),
                title = "Error",
                body = data.error?.stackTraceToString() ?: "Not know",
                linkActionText = "Try again",
                onClickLink = {
                    viewModel.settings()
                }
            )
        }
        ResponseStatus.LOADING -> {
            CarouselShimmer(
                modifier = Modifier
                    .padding(top = paddingValues.calculateTopPadding()),
                quantity = 3
            )

        }
        else -> Unit
    }
}