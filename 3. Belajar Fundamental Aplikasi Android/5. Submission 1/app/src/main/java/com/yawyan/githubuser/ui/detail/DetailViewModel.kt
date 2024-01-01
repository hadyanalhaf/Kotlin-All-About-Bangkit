package com.yawyan.githubuser.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yawyan.githubuser.data.api.RetrofitClient
import com.yawyan.githubuser.data.response.DetailsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {
    val user = MutableLiveData<DetailsResponse>()

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
}