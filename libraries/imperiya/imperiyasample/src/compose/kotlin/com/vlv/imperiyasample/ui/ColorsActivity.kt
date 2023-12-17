package com.vlv.imperiyasample.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import com.vlv.imperiya.core.ui.components.DefaultTopBar
import com.vlv.imperiya.core.ui.theme.TheMovieDbAppTheme
import com.vlv.imperiya.core.ui.theme.TheMovieDbTypography
import com.vlv.imperiya.core.ui.theme.*

class ColorItem(
    val main: Color,
    val mainName: String,
    val onMain: Color,
    val onMainName: String
)

class ColorsActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TheMovieDbAppTheme {
                val colors: List<ColorItem> = listOf(
                    ColorItem(
                        color_imperiya_light_primary,
                        "color_imperiya_light_primary",
                        color_imperiya_light_onPrimary,
                        "color_imperiya_light_onPrimary"
                    ),
                    ColorItem(
                        color_imperiya_dark_primary,
                        "color_imperiya_dark_primary",
                        color_imperiya_dark_onPrimary,
                        "color_imperiya_dark_onPrimary"
                    ),
                    ColorItem(
                        color_imperiya_light_primaryContainer,
                        "color_imperiya_light_primaryContainer",
                        color_imperiya_light_onPrimaryContainer,
                        "color_imperiya_light_onPrimaryContainer"
                    ),
                    ColorItem(
                        color_imperiya_dark_primaryContainer,
                        "color_imperiya_dark_primaryContainer",
                        color_imperiya_dark_onPrimaryContainer,
                        "color_imperiya_dark_onPrimaryContainer"
                    ),
                    ColorItem(
                        color_imperiya_light_secondary,
                        "color_imperiya_light_secondary",
                        color_imperiya_light_onSecondary,
                        "color_imperiya_light_onSecondary"
                    ),
                    ColorItem(
                        color_imperiya_dark_secondary,
                        "color_imperiya_dark_secondary",
                        color_imperiya_dark_onSecondary,
                        "color_imperiya_dark_onSecondary"
                    ),

                    ColorItem(
                        color_imperiya_light_secondaryContainer,
                        "color_imperiya_light_secondaryContainer",
                        color_imperiya_light_onSecondaryContainer,
                        "color_imperiya_light_onSecondaryContainer"
                    ),
                    ColorItem(
                        color_imperiya_dark_secondaryContainer,
                        "color_imperiya_dark_secondaryContainer",
                        color_imperiya_dark_onSecondaryContainer,
                        "color_imperiya_dark_onSecondaryContainer"
                    ),

                    ColorItem(
                        color_imperiya_light_tertiary,
                        "color_imperiya_light_tertiary",
                        color_imperiya_light_onTertiary,
                        "color_imperiya_light_onTertiary"
                    ),
                    ColorItem(
                        color_imperiya_dark_tertiary,
                        "color_imperiya_dark_tertiary",
                        color_imperiya_dark_onTertiary,
                        "color_imperiya_dark_onTertiary"
                    ),

                    ColorItem(
                        color_imperiya_light_tertiaryContainer,
                        "color_imperiya_light_tertiaryContainer",
                        color_imperiya_light_onTertiaryContainer,
                        "color_imperiya_light_onTertiaryContainer"
                    ),
                    ColorItem(
                        color_imperiya_dark_tertiaryContainer,
                        "color_imperiya_dark_tertiaryContainer",
                        color_imperiya_dark_onTertiaryContainer,
                        "color_imperiya_dark_onTertiaryContainer"
                    ),

                    ColorItem(
                        color_imperiya_light_error,
                        "color_imperiya_light_error",
                        color_imperiya_light_onError,
                        "color_imperiya_light_onError"
                    ),
                    ColorItem(
                        color_imperiya_dark_error,
                        "color_imperiya_dark_error",
                        color_imperiya_dark_onError,
                        "color_imperiya_dark_onError"
                    ),

                    ColorItem(
                        color_imperiya_light_errorContainer,
                        "color_imperiya_light_errorContainer",
                        color_imperiya_light_onErrorContainer,
                        "color_imperiya_light_onErrorContainer"
                    ),
                    ColorItem(
                        color_imperiya_dark_errorContainer,
                        "color_imperiya_dark_errorContainer",
                        color_imperiya_dark_onErrorContainer,
                        "color_imperiya_dark_onErrorContainer"
                    ),
                    ColorItem(
                        color_imperiya_light_background,
                        "color_imperiya_light_background",
                        color_imperiya_light_onBackground,
                        "color_imperiya_light_onBackground"
                    ),

                    ColorItem(
                        color_imperiya_dark_background,
                        "color_imperiya_dark_background",
                        color_imperiya_dark_onBackground,
                        "color_imperiya_dark_onBackground"
                    ),
                    ColorItem(
                        color_imperiya_light_surface,
                        "color_imperiya_light_surface",
                        color_imperiya_light_onSurface,
                        "color_imperiya_light_onSurface"
                    ),

                    ColorItem(
                        color_imperiya_dark_surface,
                        "color_imperiya_dark_surface",
                        color_imperiya_dark_onSurface,
                        "color_imperiya_dark_onSurface"
                    ),
                    ColorItem(
                        color_imperiya_light_surfaceVariant,
                        "color_imperiya_light_surfaceVariant",
                        color_imperiya_light_onSurfaceVariant,
                        "color_imperiya_light_onSurfaceVariant"
                    ),

                    ColorItem(
                        color_imperiya_dark_surfaceVariant,
                        "color_imperiya_dark_surfaceVariant",
                        color_imperiya_dark_onSurfaceVariant,
                        "color_imperiya_dark_onSurfaceVariant"
                    ),
                    ColorItem(
                        color_imperiya_light_outline,
                        "color_imperiya_light_outline",
                        Title_Color,
                        "Title_Color"
                    ),
                    ColorItem(
                        color_imperiya_dark_outline,
                        "color_imperiya_dark_outline",
                        Title_Color,
                        "Title_Color"
                    ),
                    ColorItem(
                        color_imperiya_light_inverseSurface,
                        "color_imperiya_light_inverseSurface",
                        color_imperiya_light_inverseOnSurface,
                        "color_imperiya_light_inverseOnSurface"
                    ),
                    ColorItem(
                        color_imperiya_dark_inverseSurface,
                        "color_imperiya_dark_inverseSurface",
                        color_imperiya_dark_inverseOnSurface,
                        "color_imperiya_dark_inverseOnSurface"
                    ),
                    ColorItem(
                        color_imperiya_light_surfaceTint,
                        "color_imperiya_light_surfaceTint",
                        Title_Color,
                        "Title_Color"
                    ),
                    ColorItem(
                        color_imperiya_dark_surfaceTint,
                        "color_imperiya_dark_surfaceTint",
                        Title_Color,
                        "Title_Color"
                    ),
                    ColorItem(
                        color_imperiya_light_outlineVariant,
                        "color_imperiya_light_outlineVariant",
                        Title_Color,
                        "Title_Color"
                    ),
                    ColorItem(
                        color_imperiya_dark_outlineVariant,
                        "color_imperiya_dark_outlineVariant",
                        Title_Color,
                        "Title_Color"
                    ),
                    ColorItem(
                        color_imperiya_light_scrim,
                        "color_imperiya_light_scrim",
                        Title_Color,
                        "Title_Color"
                    ),
                    ColorItem(
                        color_imperiya_dark_scrim,
                        "color_imperiya_dark_scrim",
                        Title_Color,
                        "Title_Color"
                    )
                )
                Colors(colors) {
                    finish()
                }
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Colors(colors: List<ColorItem>, finish: () -> Unit) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            DefaultTopBar(title = "Colors", onBackButton = finish)
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = it.calculateTopPadding())
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 12.dp),
                text = "Colors:",
                style = TheMovieDbTypography.TitleStyle,
                color = MaterialTheme.colorScheme.onBackground
            )           
            LazyVerticalGrid(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 12.dp),
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(
                    all = 16.dp
                ),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                content = {
                    items(colors) { colorItem ->
                        ColorItem(colorItem = colorItem)
                    }
                }
            )
        }
    }
}

@Composable
fun ColorItem(colorItem: ColorItem) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = colorItem.main,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(all = 16.dp),
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = colorItem.mainName,
            style = TheMovieDbTypography.ParagraphBoldStyle,
            color = colorItem.onMain
        )
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = colorItem.onMainName,
            style = TheMovieDbTypography.ParagraphStyle,
            color = colorItem.onMain
        )
    }
}