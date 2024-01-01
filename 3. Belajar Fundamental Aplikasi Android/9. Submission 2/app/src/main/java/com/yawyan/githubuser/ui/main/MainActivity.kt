package com.yawyan.githubuser.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.yawyan.githubuser.R
import com.yawyan.githubuser.databinding.ActivityMainBinding
import com.yawyan.githubuser.ui.favoriteuser.FavoriteUserActivity
import com.yawyan.githubuser.ui.settheme.SetThemeActivity
import com.yawyan.githubuser.ui.settheme.ThemePrefrence
import com.yawyan.githubuser.ui.settheme.dataStore


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: UserAdapter
    private lateinit var themePreference: ThemePrefrence


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(MainViewModel::class.java)

        viewModel.listUsers.observe(this) { user ->
            adapter = UserAdapter(user)
            binding.rvUser.adapter = adapter

            showLoading(false)
        }

        binding.apply {
            rvUser.layoutManager = LinearLayoutManager(this@MainActivity)
            rvUser.setHasFixedSize(true)

        }
        searchUser()

        themePreference = ThemePrefrence.getInstance(dataStore)
        observeThemeSetting()

    }

    private fun searchUser() {
        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { textView, actionId, event ->
                    searchBar.text = searchView.text
                    searchView.hide()
                    viewModel.setSearchUsers(searchBar.text.toString())
                    showLoading(true)
                    false

                }
        }
        showLoading(true)
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.fav_menu -> {
                Intent(this, FavoriteUserActivity::class.java).also {
                    startActivity(it)
                }
            }

            R.id.theme_menu -> {
                Intent(this, SetThemeActivity::class.java).also {
                    startActivity(it)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun observeThemeSetting() {
        themePreference.getThemeSetting().asLiveData().observe(this) { darkModeCheck ->
            if (darkModeCheck) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }

        }
    }

}