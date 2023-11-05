package com.vlv.common.ui.adapter.people

import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.RecyclerView
import br.com.arch.toolkit.delegate.viewProvider
import com.vlv.ui.R
import com.vlv.common.data.people.People
import com.vlv.common.ui.extension.loadUrl
import com.vlv.extensions.addAccessibilityDelegate
import com.vlv.extensions.inflate
import com.vlv.data.network.database.data.ImageType

class PeopleDiffUtil: ItemCallback<People>() {

    override fun areContentsTheSame(oldItem: People, newItem: People): Boolean {
        return oldItem.name == newItem.name && oldItem.department == newItem.department
    }

    override fun areItemsTheSame(oldItem: People, newItem: People): Boolean {
        return oldItem.id == newItem.id
    }

}

const val VIEW_TYPE_PEOPLE = 2398

class PeoplePagingAdapter(
    private val onClick: (View, People) -> Unit
): PagingDataAdapter<People, PeopleViewHolder>(PeopleDiffUtil()) {

    override fun onBindViewHolder(holder: PeopleViewHolder, position: Int) {
        val item = getItem(position) ?: return
        holder.bind(item)
        holder.avatar.setOnClickListener {
            onClick.invoke(it, item)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if(itemCount == position || peek(position) == null) super.getItemViewType(position)
        else VIEW_TYPE_PEOPLE
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeopleViewHolder {
        return PeopleViewHolder(parent.inflate(R.layout.common_people_listing_item))
    }

}

class PeopleViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val name: AppCompatTextView by viewProvider(R.id.name)
    val avatar: AppCompatImageView by viewProvider(R.id.avatar)

    fun bind(people: People) {
        avatar.clipToOutline = true
        name.text = people.name
        avatar.contentDescription =
            avatar.context.getString(R.string.common_people_avatar_content_description)
        avatar.addAccessibilityDelegate(R.string.common_open_people_detail)
        people.profilePath.loadUrl(avatar, ImageType.PROFILE)
    }

}
