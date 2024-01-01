package com.yawyan.githubuser.ui.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yawyan.githubuser.data.api.RetrofitClient
import com.yawyan.githubuser.data.response.ItemsItem
import com.yawyan.githubuser.data.response.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    val listUsers = MutableLiveData<List<ItemsItem>>()

    init {
        setSearchUsers("hadyan")
    }

    fun setSearchUsers(query: String) {
        RetrofitClient.apiInstance
            .getSearchUsers(query, "100")
            .enqueue(object : Callback<UserResponse> {

                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {
                    if (response.isSuccessful) {
                        listUsers.value = response.body()?.items
                    }
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    Log.d("Gagal", t.message.toString())
                }

            })
    }
}