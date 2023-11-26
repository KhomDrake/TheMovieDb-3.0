package com.vlv.configuration.presentation.ui.widget

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.vlv.bondsmith.data.ResponseStatus
import com.vlv.configuration.presentation.ui.SettingsViewModel
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
            val settingsDataUI = data.data ?: return
            SettingsItems(
                settingsDataUI = settingsDataUI,
                paddingValues = paddingValues,
                onConfirmChangeItem = { sectionData, item ->
                    viewModel.setData(sectionData, item)
                }
            )
        }
        ResponseStatus.ERROR -> {
            Text(text = "Error", color = MaterialTheme.colorScheme.onBackground)
        }
        ResponseStatus.LOADING -> {
            Text(text = "Loading", color = MaterialTheme.colorScheme.onBackground)
        }
        else -> Unit
    }
}