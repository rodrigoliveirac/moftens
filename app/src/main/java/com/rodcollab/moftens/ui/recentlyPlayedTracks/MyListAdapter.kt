package com.rodcollab.moftens.ui.recentlyPlayedTracks

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.rodcollab.moftens.databinding.ItemBinding
import com.rodcollab.moftens.domain.SongItem

class MyListAdapter : RecyclerView.Adapter<MyListAdapter.SongItemViewHolder>() {

    private val asyncListDiffer: AsyncListDiffer<SongItem> = AsyncListDiffer(this, DiffCallback)

    fun submitList(list: List<SongItem>) {
        asyncListDiffer.submitList(list)
    }

    class SongItemViewHolder(private val binding: ItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: SongItem) {
            binding.songName.text = item.name
            binding.artistName.text = item.artist
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = ItemBinding.inflate(inflater, parent, false)
        return SongItemViewHolder(view)
    }

    override fun getItemCount(): Int = asyncListDiffer.currentList.size

    override fun onBindViewHolder(holder: SongItemViewHolder, position: Int) {
        val item = asyncListDiffer.currentList[position]
        holder.bind(item)
    }

    object DiffCallback : DiffUtil.ItemCallback<SongItem>() {
        override fun areItemsTheSame(oldItem: SongItem, newItem: SongItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: SongItem, newItem: SongItem): Boolean {
            return oldItem == newItem
        }

    }
}

