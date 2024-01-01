package com.dicoding.picodiploma.loginwithanimation.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.dicoding.picodiploma.loginwithanimation.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(EXTRA_NAME)
        val avatar = intent.getStringExtra(EXTRA_AVATAR)
        val desc = intent.getStringExtra(EXTRA_DESC)

        binding.apply {
            binding.tvUsername.text = username
            Glide.with(this@DetailActivity)
                .load(avatar)
                .centerCrop()
                .into(ivUser)
            binding.tvDeskripsi.text = desc
        }

    }

    companion object {
        const val EXTRA_AVATAR = "avatar"
        const val EXTRA_NAME = "name"
        const val EXTRA_DESC = "desc"

    }
}