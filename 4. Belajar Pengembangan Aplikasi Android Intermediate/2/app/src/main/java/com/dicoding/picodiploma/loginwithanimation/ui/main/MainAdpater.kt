package com.dicoding.picodiploma.loginwithanimation.ui.main

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.picodiploma.loginwithanimation.data.response.ListStoryItem
import com.dicoding.picodiploma.loginwithanimation.databinding.RvStoryBinding
import com.dicoding.picodiploma.loginwithanimation.ui.detail.DetailActivity


class MainAdpater :
    PagingDataAdapter<ListStoryItem, MainAdpater.StoryViewHolder>(DIFF_ITEM_CALLBACK) {

    inner class StoryViewHolder(val binding: RvStoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(user: ListStoryItem) {
            binding.apply {
                Glide.with(itemView)
                    .load(user.photoUrl)
                    .centerCrop()
                    .into(ivUser)
                tvUsername.text = user.name
                tvDeskripsi.text = user.description

                binding.itemuser.setOnClickListener {
                    val view = Intent(itemView.context, DetailActivity::class.java)
                    view.putExtra(DetailActivity.EXTRA_NAME, user.name)
                    view.putExtra(DetailActivity.EXTRA_AVATAR, user.photoUrl)
                    view.putExtra(DetailActivity.EXTRA_DESC, user.description)
                    itemView.context.startActivity(view)
                }

            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        val view = RvStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StoryViewHolder((view))
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    companion object {
        val DIFF_ITEM_CALLBACK = object : DiffUtil.ItemCallback<ListStoryItem>() {
            override fun areItemsTheSame(
                oldStory: ListStoryItem,
                newStory: ListStoryItem
            ): Boolean {
                return oldStory == newStory
            }

            override fun areContentsTheSame(
                oldStory: ListStoryItem,
                newStory: ListStoryItem
            ): Boolean {
                return oldStory.name == newStory.name &&
                        oldStory.description == newStory.description &&
                        oldStory.photoUrl == newStory.photoUrl
            }
        }
    }

}