package com.example.submissionstoryapp.data

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.submissionstoryapp.api.ApiInterface
import com.example.submissionstoryapp.database.StoryDatabase

class StoryRepository(private val storyDatabase: StoryDatabase, private val apiService: ApiInterface, private val token: String) {
    fun getStory(): LiveData<PagingData<ListStoryItem>>{
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory ={
                StoryPagingResource(apiService, token)
            }
        ).liveData
    }
}