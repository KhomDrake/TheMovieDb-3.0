package com.vlv.themoviedb

import android.animation.ValueAnimator
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.interpolator.view.animation.FastOutLinearInInterpolator
import br.com.arch.toolkit.delegate.viewProvider
import org.koin.androidx.viewmodel.ext.android.viewModel

class IntroActivity : AppCompatActivity(R.layout.intro_activity) {

    private val viewModel: IntroViewModel by viewModel()

    private val icon: AppCompatImageView by viewProvider(R.id.icon)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ValueAnimator.ofFloat(0f, 1f).apply {
            duration = 1000L
            interpolator = FastOutLinearInInterpolator()
            addUpdateListener {
                icon.alpha = it.animatedValue as Float
            }
        }.start()

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