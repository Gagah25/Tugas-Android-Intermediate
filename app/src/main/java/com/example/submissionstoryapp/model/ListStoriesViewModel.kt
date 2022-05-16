package com.example.submissionstoryapp.model

import android.content.Context
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.submissionstoryapp.data.ListStoryItem
import com.example.submissionstoryapp.data.StoryRepository
import com.example.submissionstoryapp.di.Injection

class ListStoriesViewModel(storyRepository: StoryRepository): ViewModel() {

    val story: LiveData<PagingData<ListStoryItem>> =
        storyRepository.getStory().cachedIn(viewModelScope)
}

class ViewModelFactory(private val context: Context): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListStoriesViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return ListStoriesViewModel(Injection.provideRepository(context)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}