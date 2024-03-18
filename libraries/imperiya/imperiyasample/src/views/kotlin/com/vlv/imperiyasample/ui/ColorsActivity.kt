package com.vlv.imperiyasample.ui

import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.arch.toolkit.delegate.viewProvider
import com.vlv.imperiyasample.R

class Colors(
    val main: Int,
    val mainName: String,
    val onMain: Int,
    val onMainName: String,
    val shouldGetColor: Boolean = true
)

class ColorsDiffUtil : DiffUtil.ItemCallback<Colors>() {
    override fun areContentsTheSame(oldItem: Colors, newItem: Colors): Boolean {
        return oldItem.main == newItem.main && oldItem.onMain == newItem.onMain
    }

    override fun areItemsTheSame(oldItem: Colors, newItem: Colors): Boolean {
        return oldItem.main == newItem.main && oldItem.onMain == newItem.onMain
    }
}

class ColorsAdapter: ListAdapter<Colors, ColorViewHolder>(ColorsDiffUtil()) {

    override fun onBindViewHolder(holder: ColorViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorViewHolder {
        return ColorViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.component_color_item, parent, false)
        )
    }

}

class ColorViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val root: ViewGroup by viewProvider(R.id.root)
    private val main: AppCompatTextView by viewProvider(R.id.main)
    private val onMain: AppCompatTextView by viewProvider(R.id.on_main)

    fun bind(color: Colors) {
        root.setBackgroundColor(
            if(color.shouldGetColor)
                ContextCompat.getColor(itemView.context, color.main)
            else color.main
        )

        val textColor = if(color.shouldGetColor)
            ContextCompat.getColor(itemView.context, color.onMain)
        else color.onMain

        main.setTextColor(textColor)
        onMain.setTextColor(textColor)

        main.text = "Background: ${color.mainName}"
        onMain.text = "Text: ${color.onMainName}"
    }

}


open class ColorsActivity : AppCompatActivity(R.layout.activity_colors) {

    private val toolbar: Toolbar by viewProvider(R.id.toolbar)
    private val colorsItems: RecyclerView by viewProvider(R.id.colors)

    open val titleText: String = "Colors"

    open val colors: List<Colors> =
        listOf(
            Colors(
                R.color.color_imperiya_light_primary,
                "light_primary",
                R.color.color_imperiya_light_onPrimary,
                "light_onPrimary"
            ),
            Colors(
                R.color.color_imperiya_dark_primary,
                "dark_primary",
                R.color.color_imperiya_dark_onPrimary,
                "dark_onPrimary"
            ),
            Colors(
                R.color.color_imperiya_light_primaryContainer,
                "light_primaryContainer",
                R.color.color_imperiya_light_onPrimaryContainer,
                "light_primaryContainer"
            ),
            Colors(
                R.color.color_imperiya_dark_primaryContainer,
                "dark_primaryContainer",
                R.color.color_imperiya_dark_onPrimaryContainer,
                "dark_onPrimaryContainer"
            ),
            Colors(
                R.color.color_imperiya_light_secondary,
                "light_secondary",
                R.color.color_imperiya_light_onSecondary,
                "light_onSecondary"
            ),
            Colors(
                R.color.color_imperiya_dark_secondary,
                "dark_secondary",
                R.color.color_imperiya_dark_onSecondary,
                "dark_onSecondary"
            ),
            Colors(
                R.color.color_imperiya_light_secondaryContainer,
                "light_secondaryContainer",
                R.color.color_imperiya_light_onSecondaryContainer,
                "light_onSecondaryContainer"
            ),
            Colors(
                R.color.color_imperiya_dark_secondaryContainer,
                "dark_secondaryContainer",
                R.color.color_imperiya_dark_onSecondaryContainer,
                "dark_onSecondaryContainer"
            ),
            Colors(
                R.color.color_imperiya_light_tertiary,
                "light_tertiary",
                R.color.color_imperiya_light_onTertiary,
                "light_onTertiary"
            ),
            Colors(
                R.color.color_imperiya_dark_tertiary,
                "dark_tertiary",
                R.color.color_imperiya_dark_onTertiary,
                "dark_onTertiary"
            ),
            Colors(
                R.color.color_imperiya_light_tertiaryContainer,
                "light_tertiaryContainer",
                R.color.color_imperiya_light_onTertiaryContainer,
                "light_onTertiaryContainer"
            ),
            Colors(
                R.color.color_imperiya_dark_tertiaryContainer,
                "dark_tertiaryContainer",
                R.color.color_imperiya_dark_onTertiaryContainer,
                "dark_onTertiaryContainer"
            ),
            Colors(
                R.color.color_imperiya_light_error,
                "light_error",
                R.color.color_imperiya_light_onError,
                "light_onError"
            ),
            Colors(
                R.color.color_imperiya_dark_error,
                "dark_error",
                R.color.color_imperiya_dark_onError,
                "dark_onError"
            ),
            Colors(
                R.color.color_imperiya_light_errorContainer,
                "light_errorContainer",
                R.color.color_imperiya_light_onErrorContainer,
                "light_onErrorContainer"
            ),
            Colors(
                R.color.color_imperiya_dark_errorContainer,
                "dark_errorContainer",
                R.color.color_imperiya_dark_onErrorContainer,
                "dark_onErrorContainer"
            ),
            Colors(
                R.color.color_imperiya_light_background,
                "light_background",
                R.color.color_imperiya_light_onBackground,
                "light_onBackground"
            ),
            Colors(
                R.color.color_imperiya_dark_background,
                "dark_background",
                R.color.color_imperiya_dark_onBackground,
                "dark_onBackground"
            ),
            Colors(
                R.color.color_imperiya_light_surface,
                "light_surface",
                R.color.color_imperiya_light_onSurface,
                "light_onSurface"
            ),
            Colors(
                R.color.color_imperiya_dark_surface,
                "dark_surface",
                R.color.color_imperiya_dark_onSurface,
                "dark_onSurface"
            ),
            Colors(
                R.color.color_imperiya_light_surfaceVariant,
                "light_surfaceVariant",
                R.color.color_imperiya_light_onSurfaceVariant,
                "light_onSurfaceVariant"
            ),
            Colors(
                R.color.color_imperiya_dark_surfaceVariant,
                "dark_surfaceVariant",
                R.color.color_imperiya_dark_onSurfaceVariant,
                "dark_onSurfaceVariant"
            ),
            Colors(
                R.color.color_imperiya_light_outline,
                "light_outline",
                R.color.imperiya_title,
                "imperiya_title"
            ),
            Colors(
                R.color.color_imperiya_dark_outline,
                "dark_outline",
                R.color.imperiya_title,
                "imperiya_title"
            ),
            Colors(
                R.color.color_imperiya_light_inverseSurface,
                "light_inverseSurface",
                R.color.color_imperiya_light_inverseOnSurface,
                "light_inverseOnSurface"
            ),
            Colors(
                R.color.color_imperiya_dark_inverseSurface,
                "dark_inverseSurface",
                R.color.color_imperiya_dark_inverseOnSurface,
                "dark_inverseOnSurface"
            ),
            Colors(
                R.color.color_imperiya_light_inversePrimary,
                "light_inversePrimary",
                R.color.imperiya_title,
                "imperiya_title"
            ),
            Colors(
                R.color.color_imperiya_dark_inversePrimary,
                "dark_inversePrimary",
                R.color.imperiya_title,
                "imperiya_title"
            ),
            Colors(
                R.color.color_imperiya_light_shadow,
                "light_shadow",
                R.color.imperiya_title,
                "imperiya_title"
            ),
            Colors(
                R.color.color_imperiya_dark_shadow,
                "dark_shadow",
                R.color.imperiya_title,
                "imperiya_title"
            ),
            Colors(
                R.color.color_imperiya_light_surfaceTint,
                "light_surfaceTint",
                R.color.imperiya_title,
                "imperiya_title"
            ),
            Colors(
                R.color.color_imperiya_dark_surfaceTint,
                "dark_surfaceTint",
                R.color.imperiya_title,
                "imperiya_title"
            ),
            Colors(
                R.color.color_imperiya_light_outlineVariant,
                "light_outlineVariant",
                R.color.imperiya_title,
                "imperiya_title"
            ),
            Colors(
                R.color.color_imperiya_dark_outlineVariant,
                "dark_outlineVariant",
                R.color.imperiya_title,
                "imperiya_title"
            ),
            Colors(
                R.color.color_imperiya_light_scrim,
                "light_scrim",
                R.color.imperiya_title,
                "imperiya_title"
            ),
            Colors(
                R.color.color_imperiya_dark_scrim,
                "dark_scrim",
                R.color.imperiya_title,
                "imperiya_title"
            )
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        toolbar.setNavigationOnClickListener {
            finish()
        }

        toolbar.title = title

        val adapter = ColorsAdapter()
        colorsItems.layoutManager = GridLayoutManager(this, 2)
        colorsItems.adapter = adapter

        adapter.submitList(colors)

    }

}

class ColorsDynamicActivity : ColorsActivity() {

    override val colors: List<Colors>
        get() = listOf(
            getColors(
                R.attr.colorPrimary,
                "colorPrimary",
                R.attr.colorOnPrimary,
                "colorOnPrimary"
            ),
            getColors(
                R.attr.colorSecondary,
                "colorSecondary",
                R.attr.colorOnSecondary,
                "colorOnSecondary"
            ),
            getColors(
                R.attr.colorTertiary,
                "colorTertiary",
                R.attr.colorOnTertiary,
                "colorOnTertiary"
            ),
            getColors(
                R.attr.colorSurface,
                "colorSurface",
                R.attr.colorOnSurface,
                "colorOnSurface"
            )
        )

    private fun getColors(
        value: Int,
        name: String,
        valueOn: Int,
        nameOn: String
    ) : Colors {
        return Colors(
            getAttrColor(value),
            name,
            getAttrColor(valueOn),
            nameOn,
            shouldGetColor = false
        )
    }

    private fun getAttrColor(value: Int) : Int = run {
        val typedValue = TypedValue();
        theme.resolveAttribute(value, typedValue, true);
        ContextCompat.getColor(this, typedValue.resourceId)
    }

    override val titleText: String
        get() = "Dynamic Colors"

}
