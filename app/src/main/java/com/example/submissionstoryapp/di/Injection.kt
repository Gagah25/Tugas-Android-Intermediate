package com.example.submissionstoryapp.di

import android.content.Context
import com.example.submissionstoryapp.api.ApiConfig
import com.example.submissionstoryapp.data.StoryRepository
import com.example.submissionstoryapp.data.UserPreference
import com.example.submissionstoryapp.database.StoryDatabase

object Injection {
    private lateinit var sharedPref: UserPreference
    fun provideRepository(context: Context): StoryRepository{
        sharedPref = UserPreference(context)
        var token = "Bearer ${sharedPref.fetchToken()}"
        val database = StoryDatabase.getDatabase(context)
        val apiService = ApiConfig.getApiService()
        return StoryRepository(database, apiService, token)
    }
}