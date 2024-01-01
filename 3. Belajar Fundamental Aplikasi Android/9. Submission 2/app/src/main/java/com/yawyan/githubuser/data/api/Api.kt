package com.yawyan.githubuser.data.api

import com.yawyan.githubuser.data.response.DetailsResponse
import com.yawyan.githubuser.data.response.ItemsItem
import com.yawyan.githubuser.data.response.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    @GET("search/users")
    fun getSearchUsers(
        @Query("q") query: String,
        @Query("per_page") per_page: String
    ): Call<UserResponse>

    @GET("users/{username}")
    fun getDetailUser(
        @Path("username") username: String
    ): Call<DetailsResponse>

    @GET("users/{username}/followers")
    fun getFollowers(
        @Path("username") username: String
    ): Call<List<ItemsItem>>

    @GET("users/{username}/following")
    fun getFollowing(
        @Path("username") username: String
    ): Call<List<ItemsItem>>
}