package com.vlv.themoviedb.ui.tv_show.favorites


import com.vlv.common.route.toFavorites
import com.vlv.data.database.data.ItemType
import com.vlv.extensions.dataState
import com.vlv.extensions.emptyState
import com.vlv.extensions.errorState
import com.vlv.extensions.loadingState
import com.vlv.favorite.ui.tv_show.TvShowFavoriteViewModel
import com.vlv.themoviedb.R
import com.vlv.themoviedb.ui.tv_show.TvShowCarouselFragment
import com.vlv.themoviedb.ui.tv_show.adapter.TvShowCarouselAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class TvShowFavoritesFragment : TvShowCarouselFragment() {

    private val viewModel: TvShowFavoriteViewModel by viewModel()

    override val titleRes: Int
        get() = R.string.favorites_title

    override fun configEmptyView() {
        emptyView.apply {
            setTitle(R.string.empty_state_text_series_favorite)
            setStateIcon(R.drawable.ic_hearts)
        }
    }

    override fun configErrorView() {
        errorView.apply {
            setTitleText(R.string.error_series_load_text_title_favorites)
            setBodyText(R.string.error_load_text_body)
            setButtonText(R.string.error_load_text_button)
            setOnClickLink {
                loadContent()
            }
        }
    }

    override fun loadContent() {
        viewModel.seriesFavorites(shouldTake = true).observe(viewLifecycleOwner) {
            data {
                if(it.isEmpty()) viewStateMachine.emptyState()
                else viewStateMachine.dataState()
                (recyclerView.adapter as? TvShowCarouselAdapter)?.submitList(it)
            }
            showLoading {
                viewStateMachine.loadingState()
            }
            error { _ ->
                viewStateMachine.errorState()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        loadContent()
    }

    override fun onClickSeeAll() {
        startActivity(requireContext().toFavorites(ItemType.TV_SHOW))
    }

}