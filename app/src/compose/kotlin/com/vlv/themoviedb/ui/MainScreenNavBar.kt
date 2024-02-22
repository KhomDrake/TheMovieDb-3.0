package com.vlv.themoviedb.ui

import androidx.compose.foundation.background
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.vlv.imperiya.core.ui.theme.TheMovieDbTypography

@Composable
fun MainScreenNavBar(
    items: List<BottomNavigationItems>,
    selectedIndex: MainScreens,
    onSelect: (MainScreens) -> Unit
) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.tertiaryContainer,
        contentColor = MaterialTheme.colorScheme.tertiaryContainer
    ) {
        items.forEach { item ->
            NavigationBarItem(
                selected = item.mainScreens == selectedIndex,
                onClick = {
                    onSelect.invoke(item.mainScreens)
                },
                icon = {
                    Icon(
                        modifier = Modifier
                            .background(Color.Transparent),
                        painter = painterResource(
                            id = if(item.mainScreens == selectedIndex)
                                item.selectedIcon
                            else
                                item.unselectedIcon
                        ),
                        contentDescription = item.title,
                        tint = MaterialTheme.colorScheme.tertiary
                    )
                },
                alwaysShowLabel = false,
                label = {
                    Text(
                        text = item.title,
                        style = TheMovieDbTypography.ParagraphBoldStyle,
                        color = MaterialTheme.colorScheme.onTertiaryContainer
                    )
                }
            )
        }
    }
}