package com.dicoding.picodiploma.loginwithanimation.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.dicoding.picodiploma.loginwithanimation.data.api.ApiService
import com.dicoding.picodiploma.loginwithanimation.data.api.RetrofitClient
import com.dicoding.picodiploma.loginwithanimation.data.pref.UserModel
import com.dicoding.picodiploma.loginwithanimation.data.pref.UserPreference
import com.dicoding.picodiploma.loginwithanimation.data.response.ErrorResponse
import com.dicoding.picodiploma.loginwithanimation.data.response.FileUploadResponse
import com.dicoding.picodiploma.loginwithanimation.data.response.ListStoryItem
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
import java.io.File

class UserRepository private constructor(
    private val userPreference: UserPreference,
    private val apiService: ApiService
) {

    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun logout() {
        userPreference.logout()
    }

    fun getStory(): LiveData<Result<List<ListStoryItem>>> = liveData {
        emit(Result.Loading)
        try {
            val userPreference = runBlocking { userPreference.getSession().first() }
            val response = RetrofitClient.getApiService(userPreference.token)
            val storyResponse = response.getStories()
            val story = storyResponse.listStory
            val storyList = story.map { data ->
                ListStoryItem(
                    data?.photoUrl,
                    data?.createdAt,
                    data?.name,
                    data?.description,
                    data?.lon,
                    data?.id,
                    data?.lat,
                )
            }
            if (storyResponse.error == false) {
                emit(Result.Success(storyList))
            } else {
                emit(Result.Error(storyResponse.message ?: "error"))
            }
        } catch (
            e: HttpException
        ) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorBody.message
            emit(Result.Error("error $errorMessage"))
        }

    }

    fun postStory(photoFile: File, description: String) = liveData {
        val requestBody = description.toRequestBody("text/plain".toMediaType())
        val requestPhotoFile = photoFile.asRequestBody("image/jpeg".toMediaType())
        val multipartBody = MultipartBody.Part.createFormData(
            "photo", photoFile.name, requestPhotoFile
        )
        emit(Result.Loading)
        try {
            val pref = runBlocking { userPreference.getSession().first() }
            val response = RetrofitClient.getApiService(pref.token)
            val succes = response.postStory(multipartBody, requestBody)
            emit(Result.Success(succes))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, FileUploadResponse::class.java)
            emit(errorResponse?.message?.let { Result.Error(it) })
        }
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            userPreference: UserPreference,
            apiService: ApiService
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(userPreference, apiService)
            }.also { instance = it }
    }
}