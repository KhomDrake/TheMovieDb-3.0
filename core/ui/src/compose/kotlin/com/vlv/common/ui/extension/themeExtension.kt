package com.vlv.common.ui.extension

import androidx.compose.runtime.Composable
import com.vlv.configuration.domain.model.SettingOption
import com.vlv.data.local.datastore.DataVault
import com.vlv.imperiya.core.ui.theme.TheMovieDbAppTheme

fun isDynamicColorsEnable() = DataVault.cachedDataBoolean(
    SettingOption.DYNAMIC_COLORS.name
)

@Composable
fun TheMovieDbThemeWithDynamicColors(
    content: @Composable () -> Unit
) {
    TheMovieDbAppTheme(
        content = content,
        shouldUseDynamicColors = isDynamicColorsEnable()
    )
}