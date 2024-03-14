package com.vlv.people.ui.detail

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.vlv.common.data.people.People
import com.vlv.common.route.EXTRA_PEOPLE
import com.vlv.common.route.handleRoute
import com.vlv.common.ui.extension.TheMovieDbThemeWithDynamicColors
import com.vlv.extensions.parcelable

class PeopleDetailActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val people = intent.extras?.parcelable<People>(EXTRA_PEOPLE) ?: return finish()
        setContent {
            TheMovieDbThemeWithDynamicColors {
                Scaffold(
                    topBar = {
                        PeopleTabBar(
                            people = people,
                            onBack = {
                                finish()
                            }
                        )
                    }
                ) {
                    PeopleContent(
                        people = people,
                        routeNavigation = { route, data ->
                            handleRoute(route, data)
                        },
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = it.calculateTopPadding())
                    )
                }
            }
        }
    }

}