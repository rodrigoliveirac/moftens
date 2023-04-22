package com.rodcollab.moftens.users.topItems.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.rodcollab.moftens.databinding.ItemBinding
import com.rodcollab.moftens.users.topItems.model.TopItemElement

class TopItemsAdapter :
    RecyclerView.Adapter<TopItemsAdapter.TopItemViewHolder>() {


    private val asyncListDiffer = AsyncListDiffer(this, DiffCallback)

    class TopItemViewHolder(private val binding: ItemBinding) : ViewHolder(binding.root) {
        fun bind(topItem: TopItemElement) {
            binding.artistName.text = topItem.id
        }
    }

    fun submitList(list: List<TopItemElement>) {
        asyncListDiffer.submitList(list)
    }

    override fun onBindViewHolder(holder: TopItemViewHolder, position: Int) {
        holder.bind(asyncListDiffer.currentList[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = ItemBinding.inflate(layoutInflater, parent, false)
        return TopItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return asyncListDiffer.currentList.size
    }

    object DiffCallback : DiffUtil.ItemCallback<TopItemElement>() {
        override fun areItemsTheSame(oldItem: TopItemElement, newItem: TopItemElement): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: TopItemElement, newItem: TopItemElement): Boolean {
            return oldItem == newItem
        }
    }
}