package com.vlv.themoviedb.ui.series.favorites


import com.vlv.common.ui.route.toFavorites
import com.vlv.extensions.dataState
import com.vlv.extensions.emptyState
import com.vlv.extensions.errorState
import com.vlv.extensions.loadingState
import com.vlv.favorite.ui.series.SeriesFavoriteViewModel
import com.vlv.network.database.data.FavoriteType
import com.vlv.themoviedb.R
import com.vlv.themoviedb.ui.series.SeriesCarouselFragment
import com.vlv.themoviedb.ui.series.adapter.SeriesCarouselAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class SeriesFavoritesFragment : SeriesCarouselFragment() {

    private val viewModel: SeriesFavoriteViewModel by viewModel()

    override val titleRes: Int
        get() = R.string.favorites_title

    override fun configEmptyView() {
        emptyView.apply {
            setTitle(R.string.empty_state_text_series_favorite)
            setStateIcon(com.vlv.imperiya.core.R.drawable.ic_hearts)
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
                (recyclerView.adapter as? SeriesCarouselAdapter)?.submitList(it)
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
        startActivity(requireContext().toFavorites(FavoriteType.SERIES))
    }

}