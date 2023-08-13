package com.vlv.movie.ui.detail.about

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import br.com.arch.toolkit.delegate.extraProvider
import br.com.arch.toolkit.delegate.viewProvider
import com.vlv.movie.R
import com.vlv.movie.data.Movie
import org.koin.androidx.viewmodel.ext.android.viewModel

class AboutFragment : Fragment(R.layout.fragment_about) {

    private val movie: Movie? by extraProvider("movie")

    private val viewModel: AboutMovieViewModel by viewModel()

    private val description: AppCompatTextView by viewProvider(R.id.description)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val movie = movie ?: return

        viewModel.movieDetail(movie.id).observe(viewLifecycleOwner) {
            data {
                description.text = movie.overview
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