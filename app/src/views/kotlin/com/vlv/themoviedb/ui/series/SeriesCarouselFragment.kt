package com.vlv.themoviedb.ui.series

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
import com.vlv.common.ui.route.toSeriesDetail
import com.vlv.extensions.stateData
import com.vlv.extensions.stateEmpty
import com.vlv.extensions.stateLoading
import com.vlv.imperiya.ui.CarouselDecorator
import com.vlv.series.data.toDetailObject
import com.vlv.themoviedb.R
import com.vlv.common.R as RCommon
import com.vlv.themoviedb.ui.series.adapter.SeriesCarouselAdapter
import ru.tinkoff.scrollingpagerindicator.ScrollingPagerIndicator

abstract class SeriesCarouselFragment : Fragment(R.layout.series_list_fragment) {

    protected val title: AppCompatTextView by viewProvider(R.id.title)
    protected val recyclerView: RecyclerView by viewProvider(R.id.series)
    protected val shimmer: ShimmerFrameLayout by viewProvider(R.id.shimmer)
    protected val emptyText: AppCompatTextView by viewProvider(R.id.empty_state_text)
    protected val indicator: ScrollingPagerIndicator by viewProvider(R.id.indicator)
    protected val seeAll: AppCompatTextView by viewProvider(R.id.see_all)

    protected open val carouselDecorator = CarouselDecorator()
    protected val viewStateMachine = ViewStateMachine()

    abstract val titleRes: Int
    open val emptyTextRes: Int = R.string.now_playing_title

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        title.text = getString(titleRes)
        emptyText.text = getString(emptyTextRes)
        setupRecyclerView()
        setupViewStateMachine()
        seeAll.setOnClickListener { onClickSeeAll() }
    }

    private fun setupRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(
            requireContext(), LinearLayoutManager.HORIZONTAL, false
        )
        recyclerView.adapter = SeriesCarouselAdapter { view, series ->
            requireContext().startActivity(
                requireContext().toSeriesDetail(series.toDetailObject()),
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
                gones(shimmer, emptyText)
            }
            stateLoading {
                visibles(shimmer)
                gones(recyclerView, emptyText, indicator, seeAll)
                onEnter {
                    shimmer.startShimmer()
                }
            }
            stateEmpty {
                visibles(emptyText)
                gones(shimmer, recyclerView, indicator, seeAll)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if(viewStateMachine.isStarted) viewStateMachine.shutdown()
    }

}