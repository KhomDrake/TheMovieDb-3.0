package com.vlv.movie.ui.detail.review

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.arch.toolkit.delegate.extraProvider
import br.com.arch.toolkit.delegate.viewProvider
import com.vlv.movie.R
import com.vlv.movie.data.Movie
import com.vlv.movie.data.Review
import com.vlv.movie.ui.detail.review.adapter.ReviewAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class ReviewFragment : Fragment(R.layout.movie_fragment_review) {

    private val title: AppCompatTextView by viewProvider(R.id.review_title)
    private val reviews: RecyclerView by viewProvider(R.id.review)

    private val viewModel: ReviewViewModel by viewModel()

    private val movie: Movie? by extraProvider("movie")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val movie = movie ?: return

        reviews.layoutManager = LinearLayoutManager(
            requireContext(), LinearLayoutManager.VERTICAL, false
        )
        reviews.adapter = ReviewAdapter()

        viewModel.movieReviews(movie.id).observe(viewLifecycleOwner) {
            data {
                val reviewList = it.resultResponses.map(::Review)
                (reviews.adapter as? ReviewAdapter)?.submitList(reviewList)
                title.text = "${it.totalResults} Reviews"
            }
        }

    }

    companion object {

        fun instance(movie: Movie) = ReviewFragment().apply {
            arguments = bundleOf(
                "movie" to movie
            )
        }

    }

}