package com.yawyan.githubuser.ui.misc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.asLiveData
import com.yawyan.githubuser.R
import com.yawyan.githubuser.ui.main.MainActivity
import com.yawyan.githubuser.ui.settheme.ThemePrefrence
import com.yawyan.githubuser.ui.settheme.dataStore

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var themePreference: ThemePrefrence

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        themePreference = ThemePrefrence.getInstance(dataStore)
        observeThemeSetting()
        supportActionBar?.hide()

        val handler = Handler()
        handler.postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)
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