package com.vlv.series.presentation.ui.detail.about

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vlv.common.ui.DetailObject
import com.vlv.common.ui.about.AboutList
import com.vlv.common.ui.about.AboutListShimmer
import com.vlv.common.ui.extension.handle
import com.vlv.imperiya.core.ui.components.SmallWarningView
import org.koin.androidx.compose.koinViewModel

@Composable
fun AboutSeriesTab(
    paddingValues: PaddingValues,
    detailObject: DetailObject,
    viewModel: AboutSeriesViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(key1 = detailObject.id, block = {
        viewModel.seriesDetail(detailObject.id)
    })

    state.handle(
        success = {
            AboutList(
                items = it.aboutItems,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = paddingValues.calculateTopPadding())
            )
        },
        error = {
            SmallWarningView(
                title = "",
                body = "",
                linkActionText = "",
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        top = paddingValues.calculateTopPadding() + 16.dp,
                        start = 16.dp,
                        end = 16.dp
                    ),
                onClickLink = {
                    viewModel.seriesDetail(detailObject.id)
                }
            )
        },
        loading = {
            AboutListShimmer(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        top = paddingValues.calculateTopPadding() + 16.dp,
                        start = 16.dp,
                        end = 16.dp,
                        bottom = 16.dp
                    )
            )
        }
    )
}