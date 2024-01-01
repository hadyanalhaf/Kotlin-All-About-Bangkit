package com.dicoding.picodiploma.loginwithanimation.ui.signup

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.picodiploma.loginwithanimation.data.api.RetrofitClient
import com.dicoding.picodiploma.loginwithanimation.databinding.ActivitySignupBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    val apiService = RetrofitClient.apiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupAction()
        playAnimation()


        binding.passwordEditText.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                validatePassword()
            }
        }

    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }




    private fun setupAction() {


        binding.signupButton.setOnClickListener {

            val name = binding.nameEditText.text.toString()
            val email =  binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
        GlobalScope.launch(Dispatchers.IO){
            try {
                val message = apiService.register(name,email,password)
            } catch (e: Exception){
                Log.e("Error", e.message.toString())
            }
        }



            AlertDialog.Builder(this).apply {
                setTitle("Yeay!")
                setMessage("Halo $name , Silahkan Lanjut Ke Menu Login!")
                setPositiveButton("Lanjut") { _, _ ->
                    finish()
                }
                create()
                show()
            }
        }
    }

    private fun validatePassword() {
        val password = binding.passwordEditText.text.toString()
        if (password.length < 8) {
            binding.passwordEditTextLayout.error = "Character must have at least 8 character"
        } else {
            binding.passwordEditTextLayout.error = null
        }
    }

    private fun playAnimation() {

        val img = ObjectAnimator.ofFloat(binding.imageView, View.ALPHA, 1f).setDuration(100)
        val title = ObjectAnimator.ofFloat(binding.nameEditTextLayout, View.ALPHA, 1f).setDuration(200)
        val message =
            ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 1f).setDuration(300)
        val login =
            ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA, 1f).setDuration(400)
        val signup =
            ObjectAnimator.ofFloat(binding.signupButton, View.ALPHA, 1f).setDuration(500)

        AnimatorSet().apply {
            playSequentially(
                img,
                title,
                message,
                login,
                signup
            )
            startDelay = 100
        }.start()
    }
}