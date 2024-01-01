package com.yawyan.githubuser.ui.favoriteuser

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.yawyan.githubuser.data.database.FavoriteDao
import com.yawyan.githubuser.data.database.FavoriteDatabase
import com.yawyan.githubuser.data.response.ItemsItem

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {

    private var userDao: FavoriteDao?
    private var userDb: FavoriteDatabase?

    init {
        userDb = FavoriteDatabase.getDatabase(application)
        userDao = userDb?.favoriteDao()
    }

    fun getFavoriteUser(): LiveData<List<ItemsItem>>? {
        return userDao?.getAllFavorite()
    }

}