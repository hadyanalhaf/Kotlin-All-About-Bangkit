package com.yawyan.githubuser.ui.follow


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yawyan.githubuser.data.api.RetrofitClient
import com.yawyan.githubuser.data.response.ItemsItem
import kotlinx.coroutines.Dispatchers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlinx.coroutines.launch

class FollowViewModel : ViewModel() {

    private val listFollowers = MutableLiveData<List<ItemsItem>>()
    val listfollowering: LiveData<List<ItemsItem>> = listFollowers

    fun setListFollowers(username: String, tab: String) {

        viewModelScope.launch(Dispatchers.IO) {
            val follow: Call<List<ItemsItem>> = if (tab == "Followers") {
                RetrofitClient.apiInstance.getFollowers(username)
            } else {
                RetrofitClient.apiInstance.getFollowing(username)
            }
            follow.enqueue(object : Callback<List<ItemsItem>> {
                override fun onResponse(
                    call: Call<List<ItemsItem>>,
                    response: Response<List<ItemsItem>>
                ) {
                    if (response.isSuccessful) {
                        listFollowers.value = response.body()
                    }
                }

                override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                    Log.d("Gagal", t.message.toString())
                }

            })
        }

    }
}