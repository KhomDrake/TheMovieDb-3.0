package com.vlv.themoviedb.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import br.com.arch.toolkit.delegate.viewProvider
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.vlv.themoviedb.R

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val bottomNavigationView: BottomNavigationView by viewProvider(R.id.bottom_navigation)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        bottomNavigationView.setupWithNavController(navController)
    }
}