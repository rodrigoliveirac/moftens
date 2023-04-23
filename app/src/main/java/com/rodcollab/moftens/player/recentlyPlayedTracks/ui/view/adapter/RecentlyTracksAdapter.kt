package com.rodcollab.moftens.player.recentlyPlayedTracks.ui.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.request.ImageRequest
import coil.transform.RoundedCornersTransformation
import com.rodcollab.moftens.player.recentlyPlayedTracks.model.SongItem
import com.rodcollab.moftens.databinding.ItemBinding
import kotlinx.coroutines.*

class RecentlyTracksAdapter : RecyclerView.Adapter<RecentlyTracksAdapter.SongItemViewHolder>() {

    private val asyncListDiffer: AsyncListDiffer<SongItem> = AsyncListDiffer(this, DiffCallback)

    fun submitList(list: List<SongItem>) {
        asyncListDiffer.submitList(list)
    }

    class SongItemViewHolder(private val binding: ItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: SongItem) {
            binding.songName.text = item.name
            binding.artistName.text = item.artist
            CoroutineScope(Dispatchers.Main).launch {
                loadImgWithCoil(binding.imageView, item.url)
            }

        }

        private suspend fun loadImgWithCoil(img: ImageView, data: String) {
            withContext(Dispatchers.IO) {
                img.load(data) {
                    actions()
                }
            }
        }

        private fun ImageRequest.Builder.actions() {
            crossfade(750)
            transformations(
                RoundedCornersTransformation(8F)
            )
            build()
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

