package com.vlv.favorite.ui.tv_show

import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.vlv.common.data.tv_show.toDetailObject
import com.vlv.common.route.toTvShowsDetail
import com.vlv.common.ui.adapter.series.SeriesAdapter
import com.vlv.extensions.dataState
import com.vlv.extensions.emptyState
import com.vlv.extensions.errorState
import com.vlv.extensions.loadingState
import com.vlv.favorite.R
import com.vlv.favorite.ui.BaseFavoriteFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class TvShowFavoritesFragment : BaseFavoriteFragment() {

    private val viewModel: TvShowFavoriteViewModel by viewModel()

    override val loadingLayout: Int
        get() = com.vlv.ui.R.layout.common_listing_series_loading

    override fun setupStateView() {
        emptyState.setStateIcon(com.vlv.imperiya.core.R.drawable.ic_tv)
        emptyState.setTitle(R.string.favorite_people_empty_state)
    }

    override fun setupRecyclerView() {
        items.layoutManager = GridLayoutManager(requireContext(), 2)
        items.adapter = SeriesAdapter { series, view ->
            val intent = requireContext().toTvShowsDetail(
                series.toDetailObject(),
                finishAfterTransition = false
            )
            startActivity(
                intent,
                ActivityOptionsCompat.makeSceneTransitionAnimation(
                    requireActivity(),
                    view,
                    getString(com.vlv.ui.R.string.common_poster_transition_name)
                ).toBundle()
            )
        }
    }

    override fun loadFavorites() {
        viewModel.seriesFavorites().observe(viewLifecycleOwner) {
            data {
                if(it.isEmpty()) viewStateMachine.emptyState() else viewStateMachine.dataState()

                (items.adapter as? SeriesAdapter)?.submitList(it)
            }
            showLoading {
                viewStateMachine.loadingState()
            }
            error { e ->
                viewStateMachine.errorState()
            }
        }
    }


}