package com.vlv.favorite.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import br.com.arch.toolkit.delegate.viewProvider
import com.vlv.extensions.addOrReplace
import com.vlv.favorite.R

class FavoritesActivity : AppCompatActivity(R.layout.favorite_activity_favorites) {

    private val toolbar: Toolbar by viewProvider(R.id.toolbar)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        toolbar.setNavigationOnClickListener {
            finish()
        }
        toolbar.navigationContentDescription = getString(com.vlv.common.R.string.common_back_content_description)

        val favoritesFragment = FavoritesFragment().apply {
            arguments = intent.extras
        }

        supportFragmentManager.addOrReplace(
            R.id.content,
            favoritesFragment,
            FavoritesFragment::class.java.name
        )
    }

}