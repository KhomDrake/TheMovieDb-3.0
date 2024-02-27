package com.vlv.common.ui.adapter

import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.arch.toolkit.delegate.viewProvider
import com.vlv.common.data.about.Information
import com.vlv.extensions.inflate
import com.vlv.ui.R


class InformationDiffUtil: DiffUtil.ItemCallback<Information>() {

    override fun areContentsTheSame(oldItem: Information, newItem: Information): Boolean {
        return oldItem.title == newItem.title && oldItem.data == newItem.data
    }

    override fun areItemsTheSame(oldItem: Information, newItem: Information): Boolean {
        return oldItem.id == newItem.id
    }

}

class InformationAdapter : ListAdapter<Information, InformationViewHolder>(InformationDiffUtil()) {

    override fun onBindViewHolder(holder: InformationViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InformationViewHolder {
        return InformationViewHolder(parent.inflate(R.layout.common_information_item))
    }

}

class InformationViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val infoTitle: AppCompatTextView by viewProvider(R.id.information_title)
    private val infoData: AppCompatTextView by viewProvider(R.id.information_data)

    fun bind(info: Information) {
        infoTitle.text = itemView.context.getString(info.title)
        infoData.text = info.data
    }

}