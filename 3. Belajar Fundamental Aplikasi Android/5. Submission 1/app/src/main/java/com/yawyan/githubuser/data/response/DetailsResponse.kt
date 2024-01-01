package com.yawyan.githubuser.data.response

import com.google.gson.annotations.SerializedName

data class DetailsResponse(

    @field:SerializedName("following_url")
    val followingUrl: String,

    @field:SerializedName("bio")
    val bio: String,

    @field:SerializedName("login")
    val login: String,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("followers_url")
    val followersUrl: String,

    @field:SerializedName("avatar_url")
    val avatarUrl: String,

    @field:SerializedName("followers")
    val followers: Int,

    @field:SerializedName("following")
    val following: Int,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("location")
    val location: String,

    @field:SerializedName("public_repos")
    val publicRepos: Int

)
