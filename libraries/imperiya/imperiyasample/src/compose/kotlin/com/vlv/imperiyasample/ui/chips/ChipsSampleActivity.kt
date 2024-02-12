package com.vlv.imperiyasample.ui.chips

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
        FilterItemData("Movie", "Movie"),
        FilterItemData("Tv Show", "Tv Show"),
        FilterItemData("People", "People"),
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
            var selectedFilter: FilterItemData? by remember {
                mutableStateOf(null)
            }

            Column {
                FilterGroup(
                    filters = filters,
                    selectedFilterItem = selectedFilter,
                    onClickFilter = { new ->
                        selectedFilter = new
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
                        text = filter.value,
                        style = TheMovieDbTypography.SubTitleBoldStyle,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        }
    }

}