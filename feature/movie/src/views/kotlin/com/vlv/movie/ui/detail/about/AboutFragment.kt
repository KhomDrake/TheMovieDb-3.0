package com.vlv.movie.ui.detail.about

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
import com.vlv.movie.ui.detail.about.adapter.GenreAdapter
import com.vlv.movie.ui.detail.about.adapter.InformationAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class AboutFragment : Fragment(R.layout.movie_fragment_about) {

    private val movie: Movie? by extraProvider("movie")

    private val viewModel: AboutMovieViewModel by viewModel()

    private val description: AppCompatTextView by viewProvider(R.id.description)
    private val genres: RecyclerView by viewProvider(R.id.genres)
    private val information: RecyclerView by viewProvider(R.id.information)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val movie = movie ?: return

        genres.layoutManager = LinearLayoutManager(
            requireContext(), LinearLayoutManager.HORIZONTAL, false
        )
        information.layoutManager = LinearLayoutManager(
            requireContext(), LinearLayoutManager.VERTICAL, false
        )
        genres.adapter = GenreAdapter()
        information.adapter = InformationAdapter()

        viewModel.movieDetail(movie.id).observe(viewLifecycleOwner) {
            data { detail ->
                description.text = movie.overview
                (genres.adapter as? GenreAdapter)?.submitList(detail.genres)
                (information.adapter as? InformationAdapter)?.submitList(detail.information)
            }
        }
    }

    companion object {

        fun instance(movie: Movie) = AboutFragment().apply {
            arguments = bundleOf(
                "movie" to movie
            )
        }

    }

}