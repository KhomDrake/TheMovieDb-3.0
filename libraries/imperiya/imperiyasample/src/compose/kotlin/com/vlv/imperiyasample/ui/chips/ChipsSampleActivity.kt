package com.vlv.imperiyasample.ui.chips

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vlv.imperiya.core.ui.components.DefaultTopBar
import com.vlv.imperiya.core.ui.components.FilterGroup
import com.vlv.imperiya.core.ui.components.FilterItemData
import com.vlv.imperiya.core.ui.theme.TheMovieDbTypography
import com.vlv.imperiyasample.SampleActivity

class ChipsSampleActivity : SampleActivity() {

    private val filters = listOf(
        FilterItemData(1, "Movie", "Movie"),
        FilterItemData(2, "Tv Show", "Tv Show"),
        FilterItemData(3, "People", "People"),
    )

    @Composable
    override fun Content() {
        Scaffold(
            topBar = {
                DefaultTopBar(title = "Chips") {
                    finish()
                }
            }
        ) {
            var selectedFilter: Int? by remember {
                mutableStateOf(null)
            }

            Column {
                FilterGroup(
                    filters = filters,
                    selectedFilterItem = selectedFilter,
                    onClickFilter = { index, new ->
                        selectedFilter = index
                    },
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentPaddingValues = PaddingValues(
                        top = it.calculateTopPadding(),
                        start = 16.dp,
                        end = 16.dp
                    )
                )

                if(selectedFilter != null) {
                    val filter = selectedFilter ?: return@Column
                    Text(
                        text = filters[filter].value,
                        style = TheMovieDbTypography.SubTitleBoldStyle,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        }
    }

}