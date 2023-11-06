package com.vlv.configuration.presentation.ui

import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.SwitchCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import br.com.arch.toolkit.delegate.viewProvider
import com.vlv.configuration.presentation.R
import com.vlv.extensions.addAccessibilityDelegate
import com.vlv.extensions.addHeadingAccessibilityDelegate
import com.vlv.extensions.inflate

enum class SettingsItemType {
    TITLE,
    SWITCH,
    NORMAL
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

class SettingsAdapter(
    private val onClickIem: (SettingsItem) -> Unit,
    private val onSwitch: (SettingsItem, Boolean) -> Unit
) : ListAdapter<SettingsItem, ViewHolder>(SettingsItemDiffUtil()) {

    override fun getItemViewType(position: Int): Int {
        return currentList[position].type.ordinal
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = currentList[position]
        when(holder) {
            is SettingsTitleViewHolder -> holder.bind(item)
            is SettingsNormalViewHolder -> {
                holder.bind(item)
                if(item.type == SettingsItemType.SWITCH) {
                    holder.switch.setOnClickListener {
                        onSwitch.invoke(item, holder.switch.isChecked)
                    }
                } else {
                    holder.itemView.setOnClickListener {
                        onClickIem.invoke(item)
                    }
                    holder.itemView.addAccessibilityDelegate(
                        R.string.configuration_options_item_description
                    )
                }
            }
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
        (itemView as? AppCompatTextView)?.apply {
            text = settingsItem.title
            addHeadingAccessibilityDelegate()
        }
    }
}

class SettingsNormalViewHolder(view: View): ViewHolder(view) {

    private val title: AppCompatTextView by viewProvider(R.id.small_warning_title)
    private val body: AppCompatTextView by viewProvider(R.id.small_warning_body)
    val switch: SwitchCompat by viewProvider(R.id.switch_settings)

    fun bind(settingsItem: SettingsItem) {
        title.text = settingsItem.title
        body.text = settingsItem.body

        body.isVisible = settingsItem.body.isNullOrBlank().not()

        switch.isChecked = settingsItem.value
        switch.isVisible = settingsItem.type == SettingsItemType.SWITCH
    }
}