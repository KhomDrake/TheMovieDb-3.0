package com.vlv.themoviedb

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class IntroActivity : AppCompatActivity() {

    private val viewModel: IntroViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loadConfig().observe(this) {
            data {
                startActivity(
                    Intent(
                        "$packageName.MAIN"
                    )
                )
            }
            error { _ ->
                startActivity(
                    Intent(
                        "$packageName.MAIN"
                    )
                )
            }
        }

    }

}