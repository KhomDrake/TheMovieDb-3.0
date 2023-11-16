package com.vlv.imperiyasample.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import com.vlv.imperiya.core.ui.theme.TheMovieDbAppTheme
import com.vlv.imperiya.core.ui.theme.Title_Color

class DynamicColorsActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TheMovieDbAppTheme(
                shouldUseDynamicColors = true,
                darkTheme = intent.extras?.getBoolean("DARK_MODE") ?: isSystemInDarkTheme()
            ) {
                val colors: List<ColorItem> = listOf(
                    ColorItem(
                        MaterialTheme.colorScheme.primary,
                        "primary",
                        MaterialTheme.colorScheme.onPrimary,
                        "onPrimary"
                    ),
                    ColorItem(
                        MaterialTheme.colorScheme.primaryContainer,
                        "primaryContainer",
                        MaterialTheme.colorScheme.onPrimaryContainer,
                        "onPrimaryContainer"
                    ),
                    ColorItem(
                        MaterialTheme.colorScheme.secondary,
                        "secondary",
                        MaterialTheme.colorScheme.onSecondary,
                        "onSecondary"
                    ),
                    ColorItem(
                        MaterialTheme.colorScheme.secondaryContainer,
                        "secondaryContainer",
                        MaterialTheme.colorScheme.onSecondaryContainer,
                        "onSecondaryContainer"
                    ),
                    ColorItem(
                        MaterialTheme.colorScheme.tertiary,
                        "tertiary",
                        MaterialTheme.colorScheme.onTertiary,
                        "onTertiary"
                    ),
                    ColorItem(
                        MaterialTheme.colorScheme.tertiaryContainer,
                        "tertiaryContainer",
                        MaterialTheme.colorScheme.onTertiaryContainer,
                        "onTertiaryContainer"
                    ),
                    ColorItem(
                        MaterialTheme.colorScheme.error,
                        "error",
                        MaterialTheme.colorScheme.onError,
                        "onError"
                    ),
                    ColorItem(
                        MaterialTheme.colorScheme.errorContainer,
                        "errorContainer",
                        MaterialTheme.colorScheme.onErrorContainer,
                        "onErrorContainer"
                    ),
                    ColorItem(
                        MaterialTheme.colorScheme.background,
                        "background",
                        MaterialTheme.colorScheme.onBackground,
                        "onBackground"
                    ),
                    ColorItem(
                        MaterialTheme.colorScheme.surface,
                        "surface",
                        MaterialTheme.colorScheme.onSurface,
                        "onSurface"
                    ),
                    ColorItem(
                        MaterialTheme.colorScheme.surfaceVariant,
                        "surfaceVariant",
                        MaterialTheme.colorScheme.onSurfaceVariant,
                        "onSurfaceVariant"
                    ),
                    ColorItem(
                        MaterialTheme.colorScheme.outline,
                        "outline",
                        Title_Color,
                        "Title_Color"
                    ),
                    ColorItem(
                        MaterialTheme.colorScheme.inverseSurface,
                        "inverseSurface",
                        MaterialTheme.colorScheme.inverseOnSurface,
                        "inverseOnSurface"
                    ),
                    ColorItem(
                        MaterialTheme.colorScheme.surfaceTint,
                        "surfaceTint",
                        Title_Color,
                        "Title_Color"
                    ),
                    ColorItem(
                        MaterialTheme.colorScheme.outlineVariant,
                        "outlineVariant",
                        Title_Color,
                        "Title_Color"
                    ),
                    ColorItem(
                        MaterialTheme.colorScheme.scrim,
                        "scrim",
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