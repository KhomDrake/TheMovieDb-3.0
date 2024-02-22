package com.vlv.people.ui.listing

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.vlv.common.data.people.PeopleListType
import com.vlv.common.route.EXTRA_PEOPLE_LIST_TYPE
import com.vlv.common.route.handleRoute
import com.vlv.imperiya.core.ui.components.DefaultTopBar
import com.vlv.imperiya.core.ui.theme.TheMovieDbAppTheme
import com.vlv.people.R

class PeopleListingActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val typeName = intent.getStringExtra(EXTRA_PEOPLE_LIST_TYPE)
        val type = PeopleListType.values().find { it.name == typeName } ?: PeopleListType.POPULAR
        setContent {
            TheMovieDbAppTheme {
                Scaffold(
                    topBar = {
                        DefaultTopBar(
                            title = when(type) {
                                PeopleListType.POPULAR ->
                                    stringResource(id = R.string.people_popular_title)
                                PeopleListType.TRENDING ->
                                    stringResource(id = R.string.people_trending_title)
                            }
                        ) {
                            finish()
                        }
                    }
                ) {
                    PeopleListingContent(
                        routeNavigation = { route, data ->
                            handleRoute(route, data)
                        },
                        type = type,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = it.calculateTopPadding())
                    )
                }
            }
        }
    }

}