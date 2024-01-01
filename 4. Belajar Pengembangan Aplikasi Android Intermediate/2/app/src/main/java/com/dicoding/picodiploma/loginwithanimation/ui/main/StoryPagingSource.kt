package com.dicoding.picodiploma.loginwithanimation.ui.main

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dicoding.picodiploma.loginwithanimation.data.api.ApiService
import com.dicoding.picodiploma.loginwithanimation.data.pref.UserPreference
import com.dicoding.picodiploma.loginwithanimation.data.response.ListStoryItem
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking


class StoryPagingSource(private val pref: UserPreference, private val apiService: ApiService) :
    PagingSource<Int, ListStoryItem>() {
    override fun getRefreshKey(state: PagingState<Int, ListStoryItem>): Int? {
        return state.anchorPosition?.let {
            val anchorPage = state.closestPageToPosition(it)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ListStoryItem> {
        return try {
            val position = params.key ?: INITIAL_PAGE_INDEX
            val token = runBlocking { pref.getSession().first().token }
            val responseData = apiService.getStories(position, params.loadSize)
            val story = responseData.listStory
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

            if (token.isNotEmpty()) {
                LoadResult.Page(
                    data = storyList,
                    prevKey = if (position == INITIAL_PAGE_INDEX) null else position - 1,
                    nextKey = if (responseData.listStory.size < params.loadSize) null else position + 1
                )
            } else {
                LoadResult.Error(Exception("Failed"))
            }
        } catch (e: Exception) {
            Log.d("Exception", "Load Error: ${e.message}")
            return LoadResult.Error(e)
        }
    }

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }
}