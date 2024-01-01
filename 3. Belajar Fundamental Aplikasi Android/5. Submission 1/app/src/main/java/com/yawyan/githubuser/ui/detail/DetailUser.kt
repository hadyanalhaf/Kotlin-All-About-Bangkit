package com.yawyan.githubuser.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.tabs.TabLayoutMediator
import com.yawyan.githubuser.R
import com.yawyan.githubuser.databinding.ActivityDetailUserBinding
import com.yawyan.githubuser.ui.follow.PagerAdapter

class DetailUser : AppCompatActivity() {

    companion object {
        const val USERNAME = "username"
        private val TAB = listOf(
            R.string.tabfollowers,
            R.string.tabfollowing
        )

    }

    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(USERNAME)
        val bundle = Bundle()
        bundle.putString(USERNAME, username)

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(DetailViewModel::class.java)
        showLoading(true)
        viewModel.setUserDetail(username!!)
        viewModel.getUserDetail().observe(this) {

            if (it != null) {
                val circleImage = RequestOptions.circleCropTransform()
                binding.apply {
                    detailFullname.text = it.name
                    detailUsername.text = it.login
                    detailFollowers.text = it.followers.toString()
                    detailFollowing.text = it.following.toString()
                    detailRepo.text = it.publicRepos.toString()

                    Glide.with(this@DetailUser)
                        .load(it.avatarUrl)
                        .centerCrop()
                        .apply(circleImage)
                        .into(detailAvatar)

                    showLoading(false)
                }
            }
        }


        val adapter = PagerAdapter(this, username)
        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabdetail, binding.viewPager) { tab, position ->
            val tabs = this.getString(TAB[position])
            tab.text = tabs
        }.attach()
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }

    }
}