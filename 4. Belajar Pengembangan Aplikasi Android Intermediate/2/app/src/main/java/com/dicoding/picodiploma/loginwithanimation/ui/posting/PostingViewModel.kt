package com.dicoding.picodiploma.loginwithanimation.ui.posting

import androidx.lifecycle.ViewModel
import com.dicoding.picodiploma.loginwithanimation.data.UserRepository
import java.io.File

class PostingViewModel(private val repository: UserRepository): ViewModel() {
    fun uploadImage(file: File, description: String) = repository.postStory(file,description)
}