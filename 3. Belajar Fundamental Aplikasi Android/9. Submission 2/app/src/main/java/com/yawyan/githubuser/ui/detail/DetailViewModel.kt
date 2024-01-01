package com.yawyan.githubuser.ui.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yawyan.githubuser.data.api.RetrofitClient
import com.yawyan.githubuser.data.database.FavoriteDao
import com.yawyan.githubuser.data.database.FavoriteDatabase
import com.yawyan.githubuser.data.database.FavoriteUser
import com.yawyan.githubuser.data.response.DetailsResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application) : AndroidViewModel(application) {
    val user = MutableLiveData<DetailsResponse>()

    private var userDao: FavoriteDao?
    private var userDb: FavoriteDatabase?

    init {
        userDb = FavoriteDatabase.getDatabase(application)
        userDao = userDb?.favoriteDao()
    }

    fun setUserDetail(username: String) {
        RetrofitClient.apiInstance
            .getDetailUser(username)
            .enqueue(object : Callback<DetailsResponse> {

                override fun onResponse(
                    call: Call<DetailsResponse>,
                    response: Response<DetailsResponse>
                ) {
                    if (response.isSuccessful) {
                        user.postValue(response.body())
                    }
                }


                override fun onFailure(call: Call<DetailsResponse>, t: Throwable) {
                    Log.d("Gagal", t.message.toString())
                }

            })
    }

    fun getUserDetail(): LiveData<DetailsResponse> {
        return user
    }

    fun addToFavorite(username: String, id: Int, avatarUrl: String) {
        CoroutineScope(Dispatchers.IO).launch {
            var user = FavoriteUser(
                id,
                username,
                avatarUrl
            )
            userDao?.insert(user)
        }
    }

    suspend fun checkUser(id: Int) = userDao?.checkFavorite(id)

    fun removeFavorite(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            userDao?.deleteFavorite(id)
        }
    }
}