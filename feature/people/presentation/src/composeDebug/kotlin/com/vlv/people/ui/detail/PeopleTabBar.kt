package com.vlv.people.ui.detail

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import com.vlv.common.data.people.People
import com.vlv.imperiya.core.R
import com.vlv.imperiya.core.ui.components.DefaultTopBar
import org.koin.androidx.compose.koinViewModel

@Composable
fun PeopleTabBar(
    people: People,
    onBack: () -> Unit,
    viewModel: PeopleFavoriteViewModel = koinViewModel()
) {
    LaunchedEffect(key1 = people.id, block = {
        viewModel.isFavorite(people)
    })

    val isFavorite by viewModel.favoriteState.collectAsState()

    DefaultTopBar(
        title = people.name,
        onBackButton = onBack,
        actions = {
            IconButton(onClick = {
                viewModel.changeFavorite(people, isFavorite)
            }) {
                Icon(
                    painter = painterResource(
                        id = if(isFavorite) R.drawable.ic_heart_filled else R.drawable.ic_heart_enable
                    ),
                    contentDescription = "Favorite",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }
    )
}