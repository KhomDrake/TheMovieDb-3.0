package com.vlv.imperiya.core.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val LightColors = lightColorScheme(
    primary = color_imperiya_light_primary,
    onPrimary = color_imperiya_light_onPrimary,
    primaryContainer = color_imperiya_light_primaryContainer,
    onPrimaryContainer = color_imperiya_light_onPrimaryContainer,
    secondary = color_imperiya_light_secondary,
    onSecondary = color_imperiya_light_onSecondary,
    secondaryContainer = color_imperiya_light_secondaryContainer,
    onSecondaryContainer = color_imperiya_light_onSecondaryContainer,
    tertiary = color_imperiya_light_tertiary,
    onTertiary = color_imperiya_light_onTertiary,
    tertiaryContainer = color_imperiya_light_tertiaryContainer,
    onTertiaryContainer = color_imperiya_light_onTertiaryContainer,
    error = color_imperiya_light_error,
    errorContainer = color_imperiya_light_errorContainer,
    onError = color_imperiya_light_onError,
    onErrorContainer = color_imperiya_light_onErrorContainer,
    background = color_imperiya_light_background,
    onBackground = color_imperiya_light_onBackground,
    surface = color_imperiya_light_surface,
    onSurface = color_imperiya_light_onSurface,
    surfaceVariant = color_imperiya_light_surfaceVariant,
    onSurfaceVariant = color_imperiya_light_onSurfaceVariant,
    outline = color_imperiya_light_outline,
    inverseOnSurface = color_imperiya_light_inverseOnSurface,
    inverseSurface = color_imperiya_light_inverseSurface,
    inversePrimary = color_imperiya_light_inversePrimary,
    surfaceTint = color_imperiya_light_surfaceTint,
    outlineVariant = color_imperiya_light_outlineVariant,
    scrim = color_imperiya_light_scrim,
)


private val DarkColors = darkColorScheme(
    primary = color_imperiya_dark_primary,
    onPrimary = color_imperiya_dark_onPrimary,
    primaryContainer = color_imperiya_dark_primaryContainer,
    onPrimaryContainer = color_imperiya_dark_onPrimaryContainer,
    secondary = color_imperiya_dark_secondary,
    onSecondary = color_imperiya_dark_onSecondary,
    secondaryContainer = color_imperiya_dark_secondaryContainer,
    onSecondaryContainer = color_imperiya_dark_onSecondaryContainer,
    tertiary = color_imperiya_dark_tertiary,
    onTertiary = color_imperiya_dark_onTertiary,
    tertiaryContainer = color_imperiya_dark_tertiaryContainer,
    onTertiaryContainer = color_imperiya_dark_onTertiaryContainer,
    error = color_imperiya_dark_error,
    errorContainer = color_imperiya_dark_errorContainer,
    onError = color_imperiya_dark_onError,
    onErrorContainer = color_imperiya_dark_onErrorContainer,
    background = color_imperiya_dark_background,
    onBackground = color_imperiya_dark_onBackground,
    surface = color_imperiya_dark_surface,
    onSurface = color_imperiya_dark_onSurface,
    surfaceVariant = color_imperiya_dark_surfaceVariant,
    onSurfaceVariant = color_imperiya_dark_onSurfaceVariant,
    outline = color_imperiya_dark_outline,
    inverseOnSurface = color_imperiya_dark_inverseOnSurface,
    inverseSurface = color_imperiya_dark_inverseSurface,
    inversePrimary = color_imperiya_dark_inversePrimary,
    surfaceTint = color_imperiya_dark_surfaceTint,
    outlineVariant = color_imperiya_dark_outlineVariant,
    scrim = color_imperiya_dark_scrim,
)

@Composable
fun TheMovieDbAppTheme(
    shouldUseDynamicColors: Boolean = false,
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val useDynamicColors = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && shouldUseDynamicColors
    val colors = when {
        useDynamicColors && darkTheme -> dynamicDarkColorScheme(LocalContext.current)
        useDynamicColors && !darkTheme -> dynamicLightColorScheme(LocalContext.current)
        darkTheme -> DarkColors
        else -> LightColors
    }

    MaterialTheme(
        colorScheme = colors,
        content = content
    )
}