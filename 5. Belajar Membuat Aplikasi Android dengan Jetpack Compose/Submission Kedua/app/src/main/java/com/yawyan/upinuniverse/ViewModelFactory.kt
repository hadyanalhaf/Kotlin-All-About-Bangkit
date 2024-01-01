package com.yawyan.upinuniverse

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yawyan.upinuniverse.data.UpinIpinRepository
import com.yawyan.upinuniverse.ui.screen.detail.Detail
import com.yawyan.upinuniverse.ui.screen.home.Home

class ViewModelFactory(private val repository: UpinIpinRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(Home::class.java)) {
            return Home(repository) as T
        } else if (modelClass.isAssignableFrom(Detail::class.java)) {
            return Detail(repository) as T
        }
        throw IllegalAccessException("Unknown ViewModel class: " + modelClass.name)


    }
}