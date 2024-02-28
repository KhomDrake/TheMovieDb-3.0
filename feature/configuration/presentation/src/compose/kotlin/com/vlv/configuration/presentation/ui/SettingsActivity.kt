package com.vlv.configuration.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.vlv.common.route.RouteNavigation
import com.vlv.common.route.handleRoute
import com.vlv.common.ui.extension.TheMovieDbThemeWithDynamicColors
import com.vlv.configuration.presentation.R
import com.vlv.configuration.presentation.ui.widget.SettingsContent
import com.vlv.imperiya.core.ui.components.DefaultTopBar

class SettingsActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TheMovieDbThemeWithDynamicColors {
                SettingsScreen(
                    onBackButton = {
                        finish()
                    },
                    routeNavigation = { route, data ->
                        handleRoute(route, data)
                    }
                )
            }
        }
    }

}

@Composable
fun SettingsScreen(
    onBackButton: () -> Unit,
    routeNavigation: RouteNavigation
) {
    Scaffold(
        topBar = {
            DefaultTopBar(
                title = stringResource(id = R.string.configuration_toolbar_title)
            ) {
                onBackButton.invoke()
            }
        }
    ) {
        SettingsContent(
            paddingValues = it,
            routeNavigation = routeNavigation
        )
    }
}
