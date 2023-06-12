package com.vlv.themoviedb.ui.movie

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import br.com.arch.toolkit.delegate.viewProvider
import com.vlv.imperiya.ui.CarouselDecorator
import com.vlv.themoviedb.R
import com.vlv.themoviedb.ui.movie.adapter.MoviesCarouselAdapter

abstract class MovieCarouselFragment : Fragment(R.layout.movies_list_now_fragment) {

    protected val title: AppCompatTextView by viewProvider(R.id.title)
    protected val recyclerView: RecyclerView by viewProvider(R.id.trending_movies)

    protected open val carouselDecorator = CarouselDecorator()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.layoutManager = LinearLayoutManager(
            requireContext(), LinearLayoutManager.HORIZONTAL, false
        )
        recyclerView.adapter = MoviesCarouselAdapter()
        recyclerView.addItemDecoration(carouselDecorator)
        PagerSnapHelper().attachToRecyclerView(recyclerView)
    }

}