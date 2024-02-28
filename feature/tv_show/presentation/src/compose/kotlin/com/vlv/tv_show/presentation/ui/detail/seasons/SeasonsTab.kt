package com.vlv.tv_show.presentation.ui.detail.seasons

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.vlv.common.ui.DetailObject
import org.koin.androidx.compose.koinViewModel

@Composable
fun SeasonsTab(
    detailObject: DetailObject,
    viewModel: SeasonsViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(key1 = detailObject.id, block = {
        viewModel.seasons(detailObject.id)
    })

    SeasonTabContent(
        state = state,
        onTryAgain = {
            viewModel.seasons(detailObject.id)
        }
    )
}