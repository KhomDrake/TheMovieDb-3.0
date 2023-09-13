package com.vlv.common.ui.cast.adapter

import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.arch.toolkit.delegate.viewProvider
import coil.load
import com.vlv.common.R
import com.vlv.common.data.cast.Cast
import com.vlv.extensions.inflate
import com.vlv.extensions.toUrlMovieDb

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

class CastAdapter: ListAdapter<Cast, RecyclerView.ViewHolder>(CastDiffUtil()) {

    override fun getItemViewType(position: Int): Int {
        return when(position) {
            0 -> VIEW_TYPE_CAST_TITLE
            else -> VIEW_TYPE_CAST_ITEM
        }
    }

    override fun getItemCount(): Int {
        val itemCount = super.getItemCount()
        return if(itemCount == 0) itemCount else super.getItemCount() + 1
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is CastViewHolder -> {
                holder.bind(currentList[position - 1])
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
        (itemView as? AppCompatTextView)?.text = "$itemCount people"
    }

}

class CastViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val avatar: AppCompatImageView by viewProvider(R.id.avatar)
    private val personName: AppCompatTextView by viewProvider(R.id.person_name)
    private val character: AppCompatTextView by viewProvider(R.id.character)

    fun bind(cast: Cast) {
        avatar.clipToOutline = true
        cast.profilePath?.toUrlMovieDb()?.let {
            avatar.load(it) {
                crossfade(1000)
            }
        }
        personName.text = cast.name
        character.text = cast.character
    }

}