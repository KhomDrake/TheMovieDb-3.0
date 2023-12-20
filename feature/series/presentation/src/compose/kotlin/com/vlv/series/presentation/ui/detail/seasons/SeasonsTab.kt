package com.vlv.series.presentation.ui.detail.seasons

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.vlv.common.ui.DetailObject
import com.vlv.common.ui.extension.handle
import org.koin.androidx.compose.koinViewModel

@Composable
fun SeasonsTab(
    detailObject: DetailObject,
    modifier: Modifier = Modifier,
    viewModel: SeasonsViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(key1 = detailObject.id, block = {
        viewModel.seasons(detailObject.id)
    })

    state.handle(
        success = { seasons ->
            SeasonsSuccess(
                seasons = seasons,
                modifier = modifier
            )
        },
        error = {

        },
        loading = {
            SeasonsShimmer(
                modifier = modifier
            )
        }
    )
}