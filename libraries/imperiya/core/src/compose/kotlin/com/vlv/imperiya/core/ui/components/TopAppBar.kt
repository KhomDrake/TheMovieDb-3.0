package com.vlv.imperiya.core.ui.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewFontScale
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.vlv.imperiya.core.ui.theme.TheMovieDbAppTheme
import com.vlv.imperiya.core.ui.theme.TheMovieDbTypography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultTopBar(
    title: String,
    modifier: Modifier = Modifier,
    containerColor: Color = MaterialTheme.colorScheme.primaryContainer,
    titleContentColor: Color = MaterialTheme.colorScheme.onPrimaryContainer,
    iconBackColor: Color = MaterialTheme.colorScheme.onPrimaryContainer,
    actions: @Composable RowScope.() -> Unit = {},
    onBackButton: () -> Unit
) {
    TopAppBar(
        modifier = modifier,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = containerColor,
            titleContentColor = titleContentColor,
        ),
        title = {
            Text(
                title,
                style = TheMovieDbTypography.TitleStyle,
                color = titleContentColor
            )
        },
        navigationIcon = {
            IconButton(onClick = { onBackButton.invoke() }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Close the app",
                    tint = iconBackColor
                )
            }
        },
        actions = actions
    )
}

@PreviewLightDark
@PreviewFontScale
@Composable
fun TopBarPreview() {
    TheMovieDbAppTheme {
        DefaultTopBar(
            title = "Text",
            modifier = Modifier
                .fillMaxWidth(),
            onBackButton = {

            }
        )
    }
}