package com.example.artifact.view.ui.history

import android.content.ClipData.Item
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.artifact.R
import com.example.artifact.data.response.GetAllDataResponseItem
import com.example.artifact.databinding.ItemListHistoryBinding

class HistoryAdapter : ListAdapter<GetAllDataResponseItem, HistoryAdapter.MyViewHolder>(DIFF_CALLBACK) {

    private var onItemClickListener: ((GetAllDataResponseItem) -> Unit)? = null

    fun setOnItemClickListener(listener: (GetAllDataResponseItem) -> Unit) {
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
        fun bind(story: GetAllDataResponseItem) {
            Glide.with(binding.ivItemPhoto.context)
                .load(story.urlPhoto)
                .into(binding.ivItemPhoto)
            binding.tvItemName.text = story.name
            binding.tvItemSimilarity.text = story.imageTitle

            binding.root.setOnClickListener {
                onItemClickListener?.invoke(story)
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<GetAllDataResponseItem>() {
            override fun areItemsTheSame(oldItem: GetAllDataResponseItem, newItem: GetAllDataResponseItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: GetAllDataResponseItem,
                newItem: GetAllDataResponseItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}