package com.vlv.configuration.ui

import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.SwitchCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import br.com.arch.toolkit.delegate.viewProvider
import com.vlv.configuration.R
import com.vlv.extensions.inflate


enum class SettingsItemType {
    TITLE,
    SWITCH,
    NORMAL
}

enum class SettingsItemOption {
    ADULT_CONTENT,
    LANGUAGE,
    REGION,
    BACKDROP,
    LOGO,
    POSTER,
    PROFILE
}

class SettingsItem(
    val id: String,
    val type: SettingsItemType,
    val title: String,
    val body: String? = null,
    val value: Boolean = false
)

class SettingsItemDiffUtil: DiffUtil.ItemCallback<SettingsItem>() {
    override fun areContentsTheSame(oldItem: SettingsItem, newItem: SettingsItem): Boolean {
        return oldItem.body == newItem.body
            && oldItem.type == newItem.type
            && oldItem.title == newItem.title
    }

    override fun areItemsTheSame(oldItem: SettingsItem, newItem: SettingsItem): Boolean {
        return oldItem.id == newItem.id
    }
}

class SettingsAdapter : ListAdapter<SettingsItem, ViewHolder>(SettingsItemDiffUtil()) {

    override fun getItemViewType(position: Int): Int {
        return currentList[position].type.ordinal
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = currentList[position]
        when(holder) {
            is SettingsTitleViewHolder -> holder.bind(item)
            is SettingsNormalViewHolder -> holder.bind(item)
            else -> Unit
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when(viewType) {
            SettingsItemType.TITLE.ordinal ->
                SettingsTitleViewHolder(parent.inflate(R.layout.configuration_settings_title_item))
            else ->
                SettingsNormalViewHolder(parent.inflate(R.layout.configuration_settings_normal_item))
        }
    }

}

class SettingsTitleViewHolder(view: View): ViewHolder(view) {
    fun bind(settingsItem: SettingsItem) {
        (itemView as? AppCompatTextView)?.text = settingsItem.title
    }
}

class SettingsNormalViewHolder(view: View): ViewHolder(view) {

    private val title: AppCompatTextView by viewProvider(R.id.title)
    private val body: AppCompatTextView by viewProvider(R.id.body)
    private val switch: SwitchCompat by viewProvider(R.id.switch_settings)

    fun bind(settingsItem: SettingsItem) {
        title.text = settingsItem.title
        body.text = settingsItem.body

        body.isVisible = settingsItem.body.isNullOrBlank().not()

        switch.isVisible = settingsItem.type == SettingsItemType.SWITCH
    }
}