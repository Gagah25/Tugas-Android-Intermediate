package com.example.submissionstoryapp.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.submissionstoryapp.DetailStoryActivity
import com.example.submissionstoryapp.data.ListStoryItem
import com.example.submissionstoryapp.databinding.ItemListStoriesBinding
import com.example.submissionstoryapp.databinding.ItemStoryBinding

class StoryListAdapter :
    PagingDataAdapter<ListStoryItem, StoryListAdapter.MyViewHolder>(DIFF_CALLBACK){
    class MyViewHolder (private val binding: ItemListStoriesBinding) :
        RecyclerView.ViewHolder(binding.root){
        private var onClickDetail: OnClickDetail? = null

        fun bind(data: ListStoryItem) {
            binding.root.setOnClickListener {
                onClickDetail?.onItemClicked(data)

                val intent = Intent(itemView.context, DetailStoryActivity::class.java)
                intent.putExtra(DetailStoryActivity.EXTRA_USER, data.name)
                intent.putExtra(DetailStoryActivity.EXTRA_ID, data.description)
                intent.putExtra(DetailStoryActivity.EXTRA_PHOTO, data.photoUrl)

                val optionsCompat: ActivityOptionsCompat =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(
                        itemView.context as Activity, Pair(binding.imgItemPhoto, "profile"), Pair(binding.tvItemName, "name")
                    )

                itemView.context.startActivity(intent, optionsCompat.toBundle())
            }
            binding.tvItemName.text = data.name
            Glide.with(itemView).load(data.photoUrl).circleCrop().into(binding.imgItemPhoto)
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemListStoriesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    interface OnClickDetail {
        fun onItemClicked(dataUser: ListStoryItem)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListStoryItem>() {
            override fun areItemsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}