package com.vlv.imperiyasample.ui

import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorInt
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
import com.vlv.imperiya.core.R as Imperiya
import com.google.android.material.R as MaterialR

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
                Imperiya.color.md_theme_light_primary,
                "light_primary",
                Imperiya.color.md_theme_light_onPrimary,
                "light_onPrimary"
            ),
            Colors(
                Imperiya.color.md_theme_dark_primary,
                "dark_primary",
                Imperiya.color.md_theme_dark_onPrimary,
                "dark_onPrimary"
            ),
            Colors(
                Imperiya.color.md_theme_light_primaryContainer,
                "light_primaryContainer",
                Imperiya.color.md_theme_light_onPrimaryContainer,
                "light_primaryContainer"
            ),
            Colors(
                Imperiya.color.md_theme_dark_primaryContainer,
                "dark_primaryContainer",
                Imperiya.color.md_theme_dark_onPrimaryContainer,
                "dark_onPrimaryContainer"
            ),
            Colors(
                Imperiya.color.md_theme_light_secondary,
                "light_secondary",
                Imperiya.color.md_theme_light_onSecondary,
                "light_onSecondary"
            ),
            Colors(
                Imperiya.color.md_theme_dark_secondary,
                "dark_secondary",
                Imperiya.color.md_theme_dark_onSecondary,
                "dark_onSecondary"
            ),
            Colors(
                Imperiya.color.md_theme_light_secondaryContainer,
                "light_secondaryContainer",
                Imperiya.color.md_theme_light_onSecondaryContainer,
                "light_onSecondaryContainer"
            ),
            Colors(
                Imperiya.color.md_theme_dark_secondaryContainer,
                "dark_secondaryContainer",
                Imperiya.color.md_theme_dark_onSecondaryContainer,
                "dark_onSecondaryContainer"
            ),
            Colors(
                Imperiya.color.md_theme_light_tertiary,
                "light_tertiary",
                Imperiya.color.md_theme_light_onTertiary,
                "light_onTertiary"
            ),
            Colors(
                Imperiya.color.md_theme_dark_tertiary,
                "dark_tertiary",
                Imperiya.color.md_theme_dark_onTertiary,
                "dark_onTertiary"
            ),
            Colors(
                Imperiya.color.md_theme_light_tertiaryContainer,
                "light_tertiaryContainer",
                Imperiya.color.md_theme_light_onTertiaryContainer,
                "light_onTertiaryContainer"
            ),
            Colors(
                Imperiya.color.md_theme_dark_tertiaryContainer,
                "dark_tertiaryContainer",
                Imperiya.color.md_theme_dark_onTertiaryContainer,
                "dark_onTertiaryContainer"
            ),
            Colors(
                Imperiya.color.md_theme_light_error,
                "light_error",
                Imperiya.color.md_theme_light_onError,
                "light_onError"
            ),
            Colors(
                Imperiya.color.md_theme_dark_error,
                "dark_error",
                Imperiya.color.md_theme_dark_onError,
                "dark_onError"
            ),
            Colors(
                Imperiya.color.md_theme_light_errorContainer,
                "light_errorContainer",
                Imperiya.color.md_theme_light_onErrorContainer,
                "light_onErrorContainer"
            ),
            Colors(
                Imperiya.color.md_theme_dark_errorContainer,
                "dark_errorContainer",
                Imperiya.color.md_theme_dark_onErrorContainer,
                "dark_onErrorContainer"
            ),
            Colors(
                Imperiya.color.md_theme_light_background,
                "light_background",
                Imperiya.color.md_theme_light_onBackground,
                "light_onBackground"
            ),
            Colors(
                Imperiya.color.md_theme_dark_background,
                "dark_background",
                Imperiya.color.md_theme_dark_onBackground,
                "dark_onBackground"
            ),
            Colors(
                Imperiya.color.md_theme_light_surface,
                "light_surface",
                Imperiya.color.md_theme_light_onSurface,
                "light_onSurface"
            ),
            Colors(
                Imperiya.color.md_theme_dark_surface,
                "dark_surface",
                Imperiya.color.md_theme_dark_onSurface,
                "dark_onSurface"
            ),
            Colors(
                Imperiya.color.md_theme_light_surfaceVariant,
                "light_surfaceVariant",
                Imperiya.color.md_theme_light_onSurfaceVariant,
                "light_onSurfaceVariant"
            ),
            Colors(
                Imperiya.color.md_theme_dark_surfaceVariant,
                "dark_surfaceVariant",
                Imperiya.color.md_theme_dark_onSurfaceVariant,
                "dark_onSurfaceVariant"
            ),
            Colors(
                Imperiya.color.md_theme_light_outline,
                "light_outline",
                Imperiya.color.imperiya_title,
                "imperiya_title"
            ),
            Colors(
                Imperiya.color.md_theme_dark_outline,
                "dark_outline",
                Imperiya.color.imperiya_title,
                "imperiya_title"
            ),
            Colors(
                Imperiya.color.md_theme_light_inverseSurface,
                "light_inverseSurface",
                Imperiya.color.md_theme_light_inverseOnSurface,
                "light_inverseOnSurface"
            ),
            Colors(
                Imperiya.color.md_theme_dark_inverseSurface,
                "dark_inverseSurface",
                Imperiya.color.md_theme_dark_inverseOnSurface,
                "dark_inverseOnSurface"
            ),
            Colors(
                Imperiya.color.md_theme_light_inversePrimary,
                "light_inversePrimary",
                Imperiya.color.imperiya_title,
                "imperiya_title"
            ),
            Colors(
                Imperiya.color.md_theme_dark_inversePrimary,
                "dark_inversePrimary",
                Imperiya.color.imperiya_title,
                "imperiya_title"
            ),
            Colors(
                Imperiya.color.md_theme_light_shadow,
                "light_shadow",
                Imperiya.color.imperiya_title,
                "imperiya_title"
            ),
            Colors(
                Imperiya.color.md_theme_dark_shadow,
                "dark_shadow",
                Imperiya.color.imperiya_title,
                "imperiya_title"
            ),
            Colors(
                Imperiya.color.md_theme_light_surfaceTint,
                "light_surfaceTint",
                Imperiya.color.imperiya_title,
                "imperiya_title"
            ),
            Colors(
                Imperiya.color.md_theme_dark_surfaceTint,
                "dark_surfaceTint",
                Imperiya.color.imperiya_title,
                "imperiya_title"
            ),
            Colors(
                Imperiya.color.md_theme_light_outlineVariant,
                "light_outlineVariant",
                Imperiya.color.imperiya_title,
                "imperiya_title"
            ),
            Colors(
                Imperiya.color.md_theme_dark_outlineVariant,
                "dark_outlineVariant",
                Imperiya.color.imperiya_title,
                "imperiya_title"
            ),
            Colors(
                Imperiya.color.md_theme_light_scrim,
                "light_scrim",
                Imperiya.color.imperiya_title,
                "imperiya_title"
            ),
            Colors(
                Imperiya.color.md_theme_dark_scrim,
                "dark_scrim",
                Imperiya.color.imperiya_title,
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
                MaterialR.attr.colorPrimary,
                "colorPrimary",
                MaterialR.attr.colorOnPrimary,
                "colorOnPrimary"
            ),
            getColors(
                MaterialR.attr.colorSecondary,
                "colorSecondary",
                MaterialR.attr.colorOnSecondary,
                "colorOnSecondary"
            ),
            getColors(
                MaterialR.attr.colorTertiary,
                "colorTertiary",
                MaterialR.attr.colorOnTertiary,
                "colorOnTertiary"
            ),
            getColors(
                MaterialR.attr.colorSurface,
                "colorSurface",
                MaterialR.attr.colorOnSurface,
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
