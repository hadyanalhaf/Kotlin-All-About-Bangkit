package com.dicoding.picodiploma.loginwithanimation.ui.main

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.picodiploma.loginwithanimation.data.response.ListStoryItem
import com.dicoding.picodiploma.loginwithanimation.databinding.RvStoryBinding
import com.dicoding.picodiploma.loginwithanimation.ui.detail.DetailActivity

class MainAdpater(private val listStory: List<ListStoryItem>): RecyclerView.Adapter<MainAdpater.StoryViewHolder>() {

    inner class StoryViewHolder(val binding: RvStoryBinding) : RecyclerView.ViewHolder(binding.root) {

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
        val view = RvStoryBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return StoryViewHolder((view))
    }

    override fun getItemCount(): Int = listStory.size

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        holder.bind(listStory[position])
    }
}