package com.vlv.movie.ui.detail.cast

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
import com.vlv.movie.ui.detail.cast.adapter.CastAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class CastFragment : Fragment(R.layout.movie_fragment_cast) {

    private val title: AppCompatTextView by viewProvider(R.id.cast_title)
    private val cast: RecyclerView by viewProvider(R.id.cast)

    private val movie: Movie? by extraProvider("movie")

    private val viewModel: CastViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val movie = movie ?: return

        cast.layoutManager = LinearLayoutManager(
            requireContext(), LinearLayoutManager.VERTICAL, false
        )
        cast.adapter = CastAdapter()

        viewModel.movieCast(movie.id).observe(viewLifecycleOwner) {
            data { castList ->
                (cast.adapter as? CastAdapter)?.submitList(castList)
                title.text = "${castList.size} People"
            }
        }
    }

    companion object {

        fun instance(movie: Movie) = CastFragment().apply {
            arguments = bundleOf(
                "movie" to movie
            )
        }

    }

}