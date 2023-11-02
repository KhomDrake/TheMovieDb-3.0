package com.vlv.common.ui.cast.adapter

import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.arch.toolkit.delegate.viewProvider
import com.vlv.ui.R
import com.vlv.common.data.cast.Cast
import com.vlv.common.ui.extension.loadUrl
import com.vlv.extensions.addAccessibilityDelegate
import com.vlv.extensions.addHeadingAccessibilityDelegate
import com.vlv.extensions.inflate
import com.vlv.network.database.data.ImageType

class CastDiffUtil: DiffUtil.ItemCallback<Cast>() {
    override fun areContentsTheSame(oldItem: Cast, newItem: Cast): Boolean {
        return oldItem.name == newItem.name && oldItem.character == newItem.character
    }

    override fun areItemsTheSame(oldItem: Cast, newItem: Cast): Boolean {
        return oldItem.castId == newItem.castId
    }
}

private const val VIEW_TYPE_CAST_TITLE = 0
private const val VIEW_TYPE_CAST_ITEM = 1

class CastAdapter(
    private val onClickCast: (View, Cast) -> Unit
) : ListAdapter<Cast, RecyclerView.ViewHolder>(CastDiffUtil()) {

    override fun getItemViewType(position: Int): Int {
        return when(position) {
            0 -> VIEW_TYPE_CAST_TITLE
            else -> VIEW_TYPE_CAST_ITEM
        }
    }

    override fun getItemCount(): Int {
        val itemCount = super.getItemCount()
        return if(itemCount > 0) itemCount + 1 else itemCount
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is CastViewHolder -> {
                val item = currentList[position - 1]
                holder.bind(item)
                val image = holder.avatar
                holder.itemView.setOnClickListener {
                    onClickCast.invoke(image, item)
                }
            }
            is CastTitleViewHolder -> {
                holder.bind(currentList.size)
            }
            else -> Unit
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            VIEW_TYPE_CAST_TITLE -> CastTitleViewHolder(parent.inflate(R.layout.common_title_item))
            else -> CastViewHolder(parent.inflate(R.layout.common_cast_item))
        }
    }

}

class CastTitleViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(itemCount: Int) {
        (itemView as? AppCompatTextView)?.apply {
            addHeadingAccessibilityDelegate()
            text = itemView.context.getString(
                R.string.common_cast_title,
                itemCount
            )
        }
    }

}

class CastViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val avatar: AppCompatImageView by viewProvider(R.id.avatar)
    private val personName: AppCompatTextView by viewProvider(R.id.person_name)
    private val character: AppCompatTextView by viewProvider(R.id.character)

    fun bind(cast: Cast) {
        avatar.clipToOutline = true
        cast.profilePath.loadUrl(avatar, ImageType.PROFILE)
        personName.text = cast.name
        character.text = cast.character
        character.contentDescription = avatar.context.getString(
            R.string.common_cast_character_description,
            cast.character
        )
        itemView.addAccessibilityDelegate(
            R.string.common_open_people_detail
        )
    }

}