package com.vlv.movie.ui.nowplaying

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.vlv.common.ui.listing.ListingItemsActivity
import com.vlv.movie.R
import com.vlv.movie.ui.adapter.MoviePaginationAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class NowPlayingActivity : ListingItemsActivity() {

    private val viewModel: NowPlayingViewModel by viewModel()

    override val adapter: RecyclerView.Adapter<*>
        get() = MoviePaginationAdapter()

    override val title: Int
        get() = R.string.movie_title_now_playing

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            viewModel.nowPlaying().distinctUntilChanged().apply {
                collectLatest {
                    (items.adapter as? MoviePaginationAdapter)?.submitData(it)
                }
            }
        }
    }

}