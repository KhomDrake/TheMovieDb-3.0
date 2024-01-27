package com.vlv.themoviedb.ui.menu

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.vlv.common.route.RouteNavigation
import com.vlv.common.ui.extension.handle
import org.koin.androidx.compose.koinViewModel

@Composable
fun MenuScreen(
    paddingValues: PaddingValues,
    onNavigate: RouteNavigation,
    viewModel: MenuViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(key1 = Unit, block = {
        viewModel.menuItems()
    })

    state.handle(
        success = {
            MenuScreenSuccess(
                paddingValues = paddingValues,
                menuItems = it,
                onNavigate = onNavigate
            )
        },
        loading = {
            MenuScreenLoading(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = paddingValues.calculateTopPadding())
            )
        }
    )



}

