package com.yawyan.githubuser.ui.favoriteuser

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.yawyan.githubuser.data.response.ItemsItem
import com.yawyan.githubuser.databinding.ItemUserBinding
import com.yawyan.githubuser.ui.detail.DetailUserActivity

class FavoriteAdapter(private val items: List<ItemsItem>) :
    RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {


    inner class FavoriteViewHolder(val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(user: ItemsItem) {
            val circleImage = RequestOptions.circleCropTransform()

            binding.apply {
                Glide.with(itemView)
                    .load(user.avatarUrl)
                    .centerCrop()
                    .apply(circleImage)
                    .into(ivUser)
                tvUsername.text = user.login

                binding.itemuser.setOnClickListener {
                    val view = Intent(itemView.context, DetailUserActivity::class.java)
                    view.putExtra("username", user.login)
                    view.putExtra("id", user.id)
                    view.putExtra("avatarUrl", user.avatarUrl)
                    itemView.context.startActivity(view)
                }

            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val view = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder((view))
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(items[position])
    }
}