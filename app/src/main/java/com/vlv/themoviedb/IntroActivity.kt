package com.vlv.themoviedb

import android.animation.ValueAnimator
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import androidx.interpolator.view.animation.FastOutLinearInInterpolator
import br.com.arch.toolkit.delegate.viewProvider
import com.vlv.common.ui.route.intentForAction
import org.koin.androidx.viewmodel.ext.android.viewModel

class IntroActivity : AppCompatActivity(R.layout.intro_activity) {

    private val viewModel: IntroViewModel by viewModel()

    private val icon: AppCompatImageView by viewProvider(R.id.icon)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val startAnimationText = getString(R.string.logo_animation_start)
        val endAnimationText = getString(R.string.logo_animation_ending)

        ValueAnimator.ofFloat(0f, 1f).apply {
            duration = 1000L
            interpolator = FastOutLinearInInterpolator()
            doOnStart {
                icon.announceForAccessibility(startAnimationText)
            }
            doOnEnd {
                icon.announceForAccessibility(endAnimationText)
            }
            addUpdateListener {
                icon.alpha = it.animatedValue as Float
            }
        }.start()



        viewModel.loadConfig().observe(this) {
            data {
                openMain(endAnimationText)
            }
            error { _ ->
                openMain(endAnimationText)
            }
        }

    }

    private fun openMain(endAnimationText: String) {
        startActivity(intentForAction("MAIN"))
        icon.announceForAccessibility(endAnimationText)
    }

}