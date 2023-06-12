package com.vlv.themoviedb.ui.movie

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.vlv.themoviedb.R
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieFragment : Fragment(R.layout.movie_fragment) {

    private val viewModel: MovieViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.liveData().observe(viewLifecycleOwner) {
            data {

            }

        }
    }

}