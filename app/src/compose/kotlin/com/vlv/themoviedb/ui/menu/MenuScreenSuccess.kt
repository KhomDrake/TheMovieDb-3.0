package com.vlv.themoviedb.ui.menu

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.vlv.common.route.RouteNavigation
import com.vlv.imperiya.core.ui.theme.TheMovieDbTypography

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MenuScreenSuccess(
    paddingValues: PaddingValues,
    menuItems: List<MenuItem>,
    onNavigate: RouteNavigation
) {
    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                bottom = paddingValues.calculateBottomPadding()
            ),
        columns = GridCells.Fixed(3),
        contentPadding = PaddingValues(
            horizontal = 8.dp
        ),
        content = {
            menuItems.forEach { menuItem ->
                when(menuItem.type) {
                    MenuItemType.ITEM -> {
                        item(
                            span = { GridItemSpan(1) },
                            key = menuItem.id
                        ) {
                            MenuItem(
                                menuItem = menuItem,
                                onNavigate = onNavigate,
                                modifier = Modifier
                                    .animateItemPlacement()
                            )
                        }
                    }
                    MenuItemType.HEADER -> {
                        item(
                            span = { GridItemSpan(3) },
                            key = menuItem.id
                        ) {
                            MenuHeaderTitle(
                                menuItem = menuItem,
                                modifier = Modifier
                                    .animateItemPlacement()
                            )
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun MenuItem(
    menuItem: MenuItem,
    onNavigate: RouteNavigation,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(
                8.dp
            )
            .background(
                MaterialTheme.colorScheme.tertiary,
                RoundedCornerShape(16.dp)
            )
            .clickable {
                menuItem.action?.let {
                    onNavigate.invoke(it, null)
                }
            }
    ) {
        menuItem.icon?.let {
            Icon(
                painter = painterResource(id = menuItem.icon),
                contentDescription = null,
                modifier = Modifier
                    .padding(
                        top = 16.dp,
                        start = 16.dp
                    )
                    .background(
                        MaterialTheme.colorScheme.tertiaryContainer,
                        CircleShape
                    )
                    .padding(12.dp)
                    .size(20.dp),
                tint = MaterialTheme.colorScheme.onTertiaryContainer
            )
        }

        Text(
            modifier = Modifier
                .padding(16.dp),
            text = stringResource(id = menuItem.title),
            style = TheMovieDbTypography.ParagraphBoldStyle,
            color = MaterialTheme.colorScheme.onTertiary
        )
    }

}

@Composable
fun MenuHeaderTitle(
    menuItem: MenuItem,
    modifier: Modifier = Modifier
) {
    Text(
        modifier = modifier
            .padding(16.dp),
        text = stringResource(id = menuItem.title),
        style = TheMovieDbTypography.TitleBigStyle,
        color = MaterialTheme.colorScheme.onBackground
    )
}