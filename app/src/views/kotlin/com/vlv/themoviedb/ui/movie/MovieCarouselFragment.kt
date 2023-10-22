package com.vlv.themoviedb.ui.movie

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
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
import com.vlv.common.data.movie.toDetailObject
import com.vlv.common.ui.route.toMovieDetail
import com.vlv.extensions.defaultConfig
import com.vlv.extensions.stateData
import com.vlv.extensions.stateEmpty
import com.vlv.extensions.stateError
import com.vlv.extensions.stateLoading
import com.vlv.imperiya.ui.CarouselDecorator
import com.vlv.imperiya.ui.stateview.StateView
import com.vlv.imperiya.ui.warning.SmallWarningView
import com.vlv.themoviedb.R
import com.vlv.themoviedb.ui.movie.adapter.MoviesCarouselAdapter
import ru.tinkoff.scrollingpagerindicator.ScrollingPagerIndicator
import com.vlv.common.R as RCommon

abstract class MovieCarouselFragment : Fragment(R.layout.movies_list_fragment) {

    protected val root: ViewGroup by viewProvider(R.id.root)
    protected val title: AppCompatTextView by viewProvider(R.id.small_warning_title)
    protected val recyclerView: RecyclerView by viewProvider(R.id.movies)
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
        configEmptyView()
        configErrorView()
        setupRecyclerView()
        setupViewStateMachine()
        seeAll.setOnClickListener { onClickSeeAll() }
        loadContent()
    }

    private fun setupRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(
            requireContext(), LinearLayoutManager.HORIZONTAL, false
        )
        recyclerView.adapter = MoviesCarouselAdapter { view, movie ->
            val intent = requireContext().toMovieDetail(movie.toDetailObject())
            startActivity(
                intent,
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
            defaultConfig(root)

            stateData {
                visibles(recyclerView, indicator, seeAll)
                gones(errorView, shimmer, emptyView)
            }
            stateLoading {
                visibles(shimmer)
                gones(errorView, recyclerView, emptyView, indicator, seeAll)
            }
            stateEmpty {
                visibles(emptyView)
                gones(errorView, shimmer, recyclerView, indicator, seeAll)
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