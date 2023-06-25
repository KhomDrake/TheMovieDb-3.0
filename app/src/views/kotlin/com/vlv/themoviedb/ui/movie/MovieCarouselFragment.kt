package com.vlv.themoviedb.ui.movie

import android.os.Bundle
import android.support.annotation.StringRes
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import br.com.arch.toolkit.delegate.viewProvider
import br.com.arch.toolkit.statemachine.ViewStateMachine
import br.com.arch.toolkit.statemachine.setup
import com.facebook.shimmer.ShimmerFrameLayout
import com.vlv.extensions.stateData
import com.vlv.extensions.stateEmpty
import com.vlv.extensions.stateLoading
import com.vlv.imperiya.ui.CarouselDecorator
import com.vlv.themoviedb.R
import com.vlv.themoviedb.ui.movie.adapter.MoviesCarouselAdapter

abstract class MovieCarouselFragment : Fragment(R.layout.movies_list_fragment) {

    protected val title: AppCompatTextView by viewProvider(R.id.title)
    protected val recyclerView: RecyclerView by viewProvider(R.id.trending_movies)
    protected val shimmer: ShimmerFrameLayout by viewProvider(R.id.movies_shimmer)
    protected val emptyText: AppCompatTextView by viewProvider(R.id.empty_state_text)

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
    }

    private fun setupRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(
            requireContext(), LinearLayoutManager.HORIZONTAL, false
        )
        recyclerView.adapter = MoviesCarouselAdapter()
        recyclerView.addItemDecoration(carouselDecorator)
        PagerSnapHelper().attachToRecyclerView(recyclerView)
    }

    private fun setupViewStateMachine() {
        viewStateMachine.setup {
            stateData {
                visibles(recyclerView)
                gones(shimmer, emptyText)
            }
            stateLoading {
                visibles(shimmer)
                gones(recyclerView, emptyText)
                onEnter {
                    shimmer.startShimmer()
                }
            }
            stateEmpty {
                visibles(emptyText)
                gones(shimmer, recyclerView)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if(viewStateMachine.isStarted) viewStateMachine.shutdown()
    }

}