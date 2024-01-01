package com.yawyan.githubuser.ui.favoriteuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.yawyan.githubuser.databinding.ActivityFavoriteUserBinding

class FavoriteUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteUserBinding
    private lateinit var adapter: FavoriteAdapter
    private lateinit var viewModel: FavoriteViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)

        binding.apply {
            rvFavorite.layoutManager = LinearLayoutManager(this@FavoriteUserActivity)
            rvFavorite.setHasFixedSize(true)

        }

        viewModel.getFavoriteUser()?.observe(this) {


            if (it != null) {
                adapter = FavoriteAdapter(it)
                binding.rvFavorite.adapter = adapter

            }

        }


    }


}