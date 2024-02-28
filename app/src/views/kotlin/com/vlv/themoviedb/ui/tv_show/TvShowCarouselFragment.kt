package com.vlv.themoviedb.ui.tv_show

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import br.com.arch.toolkit.delegate.viewProvider
import br.com.arch.toolkit.statemachine.ViewStateMachine
import br.com.arch.toolkit.statemachine.setup
import com.facebook.shimmer.ShimmerFrameLayout
import com.vlv.common.data.tv_show.toDetailObject
import com.vlv.common.route.toTvShowsDetail
import com.vlv.extensions.addButtonAccessibilityDelegate
import com.vlv.extensions.addHeadingAccessibilityDelegate
import com.vlv.extensions.stateData
import com.vlv.extensions.stateEmpty
import com.vlv.extensions.stateError
import com.vlv.extensions.stateLoading
import com.vlv.imperiya.core.ui.CarouselDecorator
import com.vlv.imperiya.core.ui.stateview.StateView
import com.vlv.imperiya.core.ui.warning.SmallWarningView
import com.vlv.themoviedb.R
import com.vlv.themoviedb.ui.tv_show.adapter.TvShowCarouselAdapter
import ru.tinkoff.scrollingpagerindicator.ScrollingPagerIndicator
import com.vlv.ui.R as RCommon

abstract class TvShowCarouselFragment : Fragment(R.layout.tv_show_list_fragment) {

    protected val title: AppCompatTextView by viewProvider(R.id.list_title)
    protected val recyclerView: RecyclerView by viewProvider(R.id.tv_shows)
    protected val shimmer: ShimmerFrameLayout by viewProvider(R.id.shimmer)
    protected val errorView: SmallWarningView by viewProvider(R.id.error_state)
    protected val emptyView: StateView by viewProvider(R.id.empty_state)
    protected val indicator: ScrollingPagerIndicator by viewProvider(R.id.indicator)
    protected val seeAll: AppCompatTextView by viewProvider(R.id.see_all)

    protected open val carouselDecorator = CarouselDecorator()
    protected val viewStateMachine = ViewStateMachine()

    abstract val titleRes: Int

    abstract fun configEmptyView()

    abstract fun configErrorView()

    open fun loadContent() = Unit

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        title.text = getString(titleRes)
        title.addHeadingAccessibilityDelegate()
        configEmptyView()
        configErrorView()
        setupRecyclerView()
        setupViewStateMachine()
        seeAll.setOnClickListener { onClickSeeAll() }
        seeAll.addButtonAccessibilityDelegate()
    }

    private fun setupRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(
            requireContext(), LinearLayoutManager.HORIZONTAL, false
        )
        recyclerView.adapter = TvShowCarouselAdapter { view, series ->
            requireContext().startActivity(
                requireContext().toTvShowsDetail(series.toDetailObject()),
                ActivityOptionsCompat.makeSceneTransitionAnimation(
                    requireActivity(),
                    view,
                    getString(RCommon.string.common_backdrop_transition_name)
                ).toBundle()
            )
        }
        recyclerView.addItemDecoration(carouselDecorator)
        indicator.attachToRecyclerView(recyclerView)
        PagerSnapHelper().attachToRecyclerView(recyclerView)
    }

    open fun onClickSeeAll() = Unit

    private fun setupViewStateMachine() {
        viewStateMachine.setup {
            stateData {
                visibles(recyclerView, indicator, seeAll)
                gones(shimmer, emptyView, errorView)
            }
            stateLoading {
                visibles(shimmer)
                gones(recyclerView, emptyView, indicator, seeAll, errorView)
            }
            stateEmpty {
                visibles(emptyView)
                gones(shimmer, recyclerView, indicator, seeAll, errorView)
            }
            stateError {
                visibles(errorView)
                gones(emptyView, shimmer, recyclerView, indicator, seeAll)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if(viewStateMachine.isStarted) viewStateMachine.shutdown()
    }

}