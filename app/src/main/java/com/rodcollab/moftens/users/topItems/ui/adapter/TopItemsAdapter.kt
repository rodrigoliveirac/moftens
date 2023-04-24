package com.rodcollab.moftens.users.topItems.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import coil.request.ImageRequest
import coil.transform.RoundedCornersTransformation
import com.rodcollab.moftens.databinding.ItemBinding
import com.rodcollab.moftens.users.topItems.model.TopItemArtistElement
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TopItemsAdapter :
    RecyclerView.Adapter<TopItemsAdapter.TopItemViewHolder>() {


    private val asyncListDiffer = AsyncListDiffer(this, DiffCallback)

    class TopItemViewHolder(private val binding: ItemBinding) : ViewHolder(binding.root) {
        fun bind(topItem: TopItemArtistElement) {
            binding.artistName.text = topItem.name
            CoroutineScope(Dispatchers.Main).launch {
                loadImgWithCoil(binding.imageView, topItem.imgUrl)
            }
        }

        private suspend fun loadImgWithCoil(imageView: ImageView, data: String) {
            withContext(Dispatchers.IO) {
                imageView.load(data) {
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

    fun submitList(list: List<TopItemArtistElement>) {
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

    object DiffCallback : DiffUtil.ItemCallback<TopItemArtistElement>() {
        override fun areItemsTheSame(oldItem: TopItemArtistElement, newItem: TopItemArtistElement): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: TopItemArtistElement, newItem: TopItemArtistElement): Boolean {
            return oldItem == newItem
        }
    }
}