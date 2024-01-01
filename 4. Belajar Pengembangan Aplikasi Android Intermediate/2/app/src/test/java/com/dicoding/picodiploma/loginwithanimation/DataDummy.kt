package com.dicoding.picodiploma.loginwithanimation

import com.dicoding.picodiploma.loginwithanimation.data.response.ListStoryItem
import com.dicoding.picodiploma.loginwithanimation.data.response.ListStoryResponse

object DataDummy {
    fun generateDummyStories(): List<ListStoryItem> {
        val items: MutableList<ListStoryItem> = arrayListOf()
        for (i in 0..100) {
            val story = ListStoryItem(
                "photoUrl + $i",
                "createdAt + $i",
                "name + $i",
                "description + $i",
                i.toDouble(),
                i.toString(),
                i.toDouble()
            )
            items.add(story)
        }
        return items
    }
}