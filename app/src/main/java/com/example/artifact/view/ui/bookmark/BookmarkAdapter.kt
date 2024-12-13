package com.example.artifact.view.ui.bookmark

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.artifact.data.database.Bookmark
import com.example.artifact.databinding.ItemListHistoryBinding

class BookmarkAdapter : ListAdapter<Bookmark, BookmarkAdapter.MyViewHolder>(DIFF_CALLBACK) {

    private var onItemClickListener: ((Bookmark) -> Unit)? = null

    fun setOnItemClickListener(listener: (Bookmark) -> Unit) {
        onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            ItemListHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val story = getItem(position)
        holder.bind(story)
    }

    inner class MyViewHolder(val binding: ItemListHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(story: Bookmark) {
            Glide.with(binding.ivItemPhoto.context)
                .load(story.photoUrl)
                .into(binding.ivItemPhoto)
            binding.tvItemName.text = story.name
            binding.tvItemSimilarity.text = story.description

            binding.root.setOnClickListener {
                onItemClickListener?.invoke(story)
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Bookmark>() {
            override fun areItemsTheSame(oldItem: Bookmark, newItem: Bookmark): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: Bookmark,
                newItem: Bookmark
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}